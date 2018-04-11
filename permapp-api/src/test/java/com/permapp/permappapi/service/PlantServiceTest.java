package com.permapp.permappapi.service;

import static com.permapp.permappapi.compose.CategoryCompose.getCategoryDefault;
import static com.permapp.permappapi.compose.PlantCompose.getPlantDefault;
import static com.permapp.permappapi.compose.PlantDataBeanCompose.getDefaultPlantDataBean;
import static com.permapp.permappapi.compose.PlantDataBeanCompose.getPlantDataBeanWithNullName;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.permapp.permappapi.bean.PlantDataBean;
import com.permapp.permappapi.dto.PlantDTO;
import com.permapp.permappapi.entity.Category;
import com.permapp.permappapi.entity.Plant;
import com.permapp.permappapi.exception.EntityAlreadyExistsException;
import com.permapp.permappapi.exception.EntityNotFoundException;
import com.permapp.permappapi.exception.MissingFieldException;
import com.permapp.permappapi.repository.PlantRepository;
import com.permapp.permappapi.service.impl.PlantServiceImpl;
import com.permapp.permappapi.validator.MandatoryFieldsValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RunWith(MockitoJUnitRunner.class)
public class PlantServiceTest {

  @Mock
  private PlantRepository plantRepository;

  @Mock
  private CategoryService categoryService;

  @Spy
  private MandatoryFieldsValidator validator;

  @Spy
  @InjectMocks
  private PlantService plantService = new PlantServiceImpl();

  @Rule
  public ErrorCollector collector = new ErrorCollector();

  private Pageable PAGEABLE = PageRequest.of(0, MAX_VALUE);
  private Plant PLANT_MOCK = getPlantDefault().build();
  private Category CATEGORY_MOCK = getCategoryDefault().build();
  private PlantDataBean PLANT_BEAN_MOCK = getDefaultPlantDataBean().build();
  private PlantDataBean PLANT_BEAN_WITH_NULL_NAME_MOCK = getPlantDataBeanWithNullName().build();

  @Test
  public void shouldReturnAllPlants() {
    List<PlantDTO> plantDTOList = asList(
        new PlantDTO(PLANT_MOCK),
        new PlantDTO(PLANT_MOCK),
        new PlantDTO(PLANT_MOCK));

    List<Plant> plantList = asList(PLANT_MOCK, PLANT_MOCK, PLANT_MOCK);
    when(plantRepository.findAll(PAGEABLE)).thenReturn(new PageImpl<>(plantList));

    Page<PlantDTO> plants = plantService.getAll(PAGEABLE);
    verify(plantRepository).findAll(PAGEABLE);
    verifyNoMoreInteractions(plantRepository);
    collector.checkThat(plants.getContent(), is(plantDTOList));
    collector.checkThat(plants.getContent(), hasSize(plantDTOList.size()));
    collector.checkThat(plants.getContent(), hasItem(new PlantDTO(PLANT_MOCK)));
  }

  @Test
  public void shouldReturnEmptyWhenNoPlantsWereFound() {
    when(plantRepository.findAll(PAGEABLE)).thenReturn(new PageImpl<>(new ArrayList<>()));

    Page<PlantDTO> plants = plantService.getAll(PAGEABLE);

    verify(plantRepository).findAll(PAGEABLE);
    verifyNoMoreInteractions(plantRepository);
    collector.checkThat(plants.getContent(), is(new ArrayList<>()));
    collector.checkThat(plants.getContent(), hasSize(0));
  }

  @Test
  public void shouldReturnAPlantByItsID() {
    Optional<Plant> optionalPlant = Optional.of(PLANT_MOCK);
    when(plantRepository.findById(1L)).thenReturn(optionalPlant);

    Plant plant = plantService.get(1L);

    verify(plantRepository).findById(1L);
    verifyNoMoreInteractions(plantRepository);
    collector.checkThat(plant, is(PLANT_MOCK));
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldLaunchExceptionWhenNoEntityWasFound() {
    Optional<Plant> optionalPlant = Optional.empty();
    when(plantRepository.findById(1L)).thenReturn(optionalPlant);

    plantService.get(1L);
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldLaunchExceptionWhenIDIsNull() {
    Optional<Plant> optionalPlant = Optional.ofNullable(null);
    when(plantRepository.findById(null)).thenReturn(optionalPlant);

    plantService.get(null);
  }

  @Test
  public void shouldDeleteAPlantByItsID() {
    doReturn(PLANT_MOCK).when(plantService).get(1L);

    PlantDTO plantDTO = plantService.remove(1L);

    verify(plantService).get(1L);
    verify(plantRepository).delete(PLANT_MOCK);
    assertThat(plantDTO, is(new PlantDTO(PLANT_MOCK)));
  }

  @Test
  public void shouldCreateAPlant() {
    when(plantRepository.findByName("Nome Planta 1")).thenReturn(Optional.empty());
    when(categoryService.get(1L)).thenReturn(CATEGORY_MOCK);
    when(plantRepository.save(any(Plant.class))).thenReturn(PLANT_MOCK);

    plantService.create(PLANT_BEAN_MOCK);

    ArgumentCaptor<Plant> argumentCaptor = ArgumentCaptor.forClass(Plant.class);
    verify(plantRepository).save(argumentCaptor.capture());
    Plant plant = argumentCaptor.getValue();

    verify(plantRepository).findByName("Nome Planta 1");
    collector.checkThat(plant.getName(), is(PLANT_BEAN_MOCK.getName()));
    collector.checkThat(plant.getName2(), is(PLANT_BEAN_MOCK.getName2()));
    collector.checkThat(plant.getName3(), is(PLANT_BEAN_MOCK.getName3()));
    collector.checkThat(plant.getName4(), is(PLANT_BEAN_MOCK.getName4()));
    collector.checkThat(plant.getName5(), is(PLANT_BEAN_MOCK.getName5()));
    collector.checkThat(plant.getEstract(), is(PLANT_BEAN_MOCK.getEstract()));
    collector.checkThat(plant.getSpaceH(), is(PLANT_BEAN_MOCK.getSpaceH()));
    collector.checkThat(plant.getSpaceL(), is(PLANT_BEAN_MOCK.getSpaceL()));
    collector.checkThat(plant.getTimeHarvest(), is(PLANT_BEAN_MOCK.getTimeHarvest()));
    collector.checkThat(plant.getTimeRegrowth(), is(PLANT_BEAN_MOCK.getTimeRegrowth()));
    collector.checkThat(plant.getTimeLifeCicle(), is(PLANT_BEAN_MOCK.getTimeLifeCicle()));
    collector.checkThat(plant.getMudaPrice(), is(PLANT_BEAN_MOCK.getMudaPrice()));
    collector.checkThat(plant.getSeedPrice(), is(PLANT_BEAN_MOCK.getSeedPrice()));
    collector.checkThat(plant.getSalePrice(), is(PLANT_BEAN_MOCK.getSalePrice()));
    collector.checkThat(plant.getInfo(), is(PLANT_BEAN_MOCK.getInfo()));
    collector.checkThat(plant.getShading(), is(PLANT_BEAN_MOCK.getShading()));
    collector.checkThat(plant.getCategory(), is(CATEGORY_MOCK));
  }

  @Test(expected = EntityAlreadyExistsException.class)
  public void shouldLaunchExceptionWhenPlantWithNameAlreadyExistsOnCreate() {
    when(plantRepository.findByName("Nome Planta 1")).thenReturn(Optional.of(PLANT_MOCK));

    plantService.create(PLANT_BEAN_MOCK);
  }

  @Test(expected = MissingFieldException.class)
  public void shouldLaunchExceptionWhenPlantNameIsNullOnCreate() {
    plantService.create(PLANT_BEAN_WITH_NULL_NAME_MOCK);
  }

  @Test
  public void shouldUpdateAPlant() {
    doReturn(PLANT_MOCK).when(plantService).get(1L);
    when(plantRepository.findByNameExceptId("Nome Planta 1", 1L)).thenReturn(Optional.empty());
    when(categoryService.get(1L)).thenReturn(CATEGORY_MOCK);
    when(plantRepository.save(any(Plant.class))).thenReturn(PLANT_MOCK);

    plantService.update(1L, PLANT_BEAN_MOCK);

    ArgumentCaptor<Plant> argumentCaptor = ArgumentCaptor.forClass(Plant.class);
    verify(plantRepository).save(argumentCaptor.capture());
    Plant plant = argumentCaptor.getValue();

    verify(plantRepository).findByNameExceptId("Nome Planta 1", 1L);
    collector.checkThat(plant.getId(), is(1L));
    collector.checkThat(plant.getName(), is(PLANT_BEAN_MOCK.getName()));
    collector.checkThat(plant.getName2(), is(PLANT_BEAN_MOCK.getName2()));
    collector.checkThat(plant.getName3(), is(PLANT_BEAN_MOCK.getName3()));
    collector.checkThat(plant.getName4(), is(PLANT_BEAN_MOCK.getName4()));
    collector.checkThat(plant.getName5(), is(PLANT_BEAN_MOCK.getName5()));
    collector.checkThat(plant.getEstract(), is(PLANT_BEAN_MOCK.getEstract()));
    collector.checkThat(plant.getSpaceH(), is(PLANT_BEAN_MOCK.getSpaceH()));
    collector.checkThat(plant.getSpaceL(), is(PLANT_BEAN_MOCK.getSpaceL()));
    collector.checkThat(plant.getTimeHarvest(), is(PLANT_BEAN_MOCK.getTimeHarvest()));
    collector.checkThat(plant.getTimeRegrowth(), is(PLANT_BEAN_MOCK.getTimeRegrowth()));
    collector.checkThat(plant.getTimeLifeCicle(), is(PLANT_BEAN_MOCK.getTimeLifeCicle()));
    collector.checkThat(plant.getMudaPrice(), is(PLANT_BEAN_MOCK.getMudaPrice()));
    collector.checkThat(plant.getSeedPrice(), is(PLANT_BEAN_MOCK.getSeedPrice()));
    collector.checkThat(plant.getSalePrice(), is(PLANT_BEAN_MOCK.getSalePrice()));
    collector.checkThat(plant.getInfo(), is(PLANT_BEAN_MOCK.getInfo()));
    collector.checkThat(plant.getShading(), is(PLANT_BEAN_MOCK.getShading()));
    collector.checkThat(plant.getCategory(), is(CATEGORY_MOCK));
  }

  @Test(expected = EntityAlreadyExistsException.class)
  public void shouldLaunchExceptionWhenPlantWithNameAlreadyExistsOnUpdate() {
    when(plantRepository.findByNameExceptId("Nome Planta 1", 1L)).thenReturn(Optional.of(PLANT_MOCK));

    plantService.update(1L, PLANT_BEAN_MOCK);
  }

  @Test(expected = MissingFieldException.class)
  public void shouldLaunchExceptionWhenPlantNameIsNullOnUpdate() {
    plantService.update(1L, PLANT_BEAN_WITH_NULL_NAME_MOCK);
  }
}
