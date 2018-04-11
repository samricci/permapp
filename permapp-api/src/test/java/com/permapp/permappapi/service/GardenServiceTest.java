package com.permapp.permappapi.service;

import static com.permapp.permappapi.compose.GardenCompose.getGardenDefault;
import static com.permapp.permappapi.compose.GardenDataBeanCompose.getGardenDataBeanDefault;
import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.permapp.permappapi.bean.GardenDataBean;
import com.permapp.permappapi.dto.GardenDTO;
import com.permapp.permappapi.entity.Garden;
import com.permapp.permappapi.exception.EntityAlreadyExistsException;
import com.permapp.permappapi.exception.EntityNotFoundException;
import com.permapp.permappapi.exception.MissingFieldException;
import com.permapp.permappapi.repository.GardenRepository;
import com.permapp.permappapi.service.impl.GardenServiceImpl;
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
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RunWith(MockitoJUnitRunner.class)
public class GardenServiceTest {

  @Mock
  private GardenRepository gardenRepository;

  @Spy
  private MandatoryFieldsValidator validator;

  @InjectMocks
  @Spy
  private GardenService gardenService = new GardenServiceImpl();

  @Rule
  public ErrorCollector collector = new ErrorCollector();

  private Pageable PAGEABLE = PageRequest.of(0, MAX_VALUE);
  private Garden GARDEN_MOCK = getGardenDefault().build();
  private Garden GARDEN_MOCK_2 = Garden.builder().name("Garden 1234").build();
  private GardenDataBean GARDEN_BEAN_MOCK = getGardenDataBeanDefault().build();

  @Test
  public void shouldReturnAllGardens() {

    List<GardenDTO> gardenDTOList = asList(
        new GardenDTO(GARDEN_MOCK),
        new GardenDTO(GARDEN_MOCK),
        new GardenDTO(GARDEN_MOCK));

    List<Garden> gardenList = asList(GARDEN_MOCK,GARDEN_MOCK,GARDEN_MOCK);

    when(gardenRepository.findAll(PAGEABLE)).thenReturn(new PageImpl<>(gardenList));

    Page<GardenDTO> gardens = gardenService.getAll(PAGEABLE);

    verify(gardenRepository).findAll(PAGEABLE);
    verifyNoMoreInteractions(gardenRepository);
    collector.checkThat(gardens.getContent(), is(gardenDTOList));
    collector.checkThat(gardens.getContent(), hasSize(gardenDTOList.size()));
    collector.checkThat(gardens.getContent(), hasItem(new GardenDTO(GARDEN_MOCK)));
  }

  @Test
  public void shouldReturnEmptyWhenNoGardensWereFound() {
    when(gardenRepository.findAll(PAGEABLE)).thenReturn(new PageImpl<>(new ArrayList<>()));

    Page<GardenDTO> gardens = gardenService.getAll(PAGEABLE);

    verify(gardenRepository).findAll(PAGEABLE);
    verifyNoMoreInteractions(gardenRepository);
    collector.checkThat(gardens.getContent(), is(new ArrayList<>()));
    collector.checkThat(gardens.getContent(), hasSize(0));
  }

  @Test
  public void shouldReturnAGardenByItsID() {
    Optional<Garden> optionalGarden = Optional.of(GARDEN_MOCK);
    when(gardenRepository.findById(1L)).thenReturn(optionalGarden);

    Garden garden = gardenService.get(1L);

    verify(gardenRepository).findById(1L);
    verifyNoMoreInteractions(gardenRepository);
    collector.checkThat(garden, is(GARDEN_MOCK));
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldLaunchExceptionWhenGardenNotFound() {
    Optional<Garden> optionalGarden = Optional.empty();
    when(gardenRepository.findById(1L)).thenReturn(optionalGarden);

    gardenService.get(1L);
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldLaunchExceptionWhenIDIsNull() {
    Optional<Garden> optionalGarden = Optional.ofNullable(null);
    when(gardenRepository.findById(null)).thenReturn(optionalGarden);

    gardenService.get(null);
  }

  @Test
  public void shouldReturnAGardenDTO() {
    doReturn(GARDEN_MOCK).when(gardenService).get(1L);

    GardenDTO gardenDTO = gardenService.getDTO(1L);

    verify(gardenService).get(1L);
    collector.checkThat(gardenDTO, is(new GardenDTO(GARDEN_MOCK)));
  }

  @Test
  public void shouldRemoveAGardenByItsID() {
    doReturn(GARDEN_MOCK).when(gardenService).get(1L);

    GardenDTO gardenDTO = gardenService.remove(1L);

    verify(gardenService).get(1L);
    verify(gardenRepository).delete(GARDEN_MOCK);
    verifyNoMoreInteractions(gardenRepository);
    collector.checkThat(gardenDTO, is(new GardenDTO(GARDEN_MOCK)));
  }

  @Test
  public void shouldCreateAGarden() {
    when(gardenRepository.findByName("Garden 1234")).thenReturn(Optional.empty());
    when(gardenRepository.save(Mockito.any(Garden.class))).thenReturn(GARDEN_MOCK);

    GardenDTO gardenDTO = gardenService.create(GARDEN_BEAN_MOCK);

    ArgumentCaptor<Garden> argumentCaptor = ArgumentCaptor.forClass(Garden.class);
    verify(gardenRepository).save(argumentCaptor.capture());
    Garden garden = argumentCaptor.getValue();

    verify(gardenRepository).findByName("Garden 1234");
    collector.checkThat(garden.getName(), is(GARDEN_BEAN_MOCK.getName()));
    collector.checkThat(garden.getCity(), is(GARDEN_BEAN_MOCK.getCity()));
    collector.checkThat(garden.getComplement(), is(GARDEN_BEAN_MOCK.getComplement()));
    collector.checkThat(garden.getNumber(), is(GARDEN_BEAN_MOCK.getNumber()));
    collector.checkThat(garden.getZipCode(), is(GARDEN_BEAN_MOCK.getZipCode()));
    collector.checkThat(garden.getState(), is(GARDEN_BEAN_MOCK.getState()));
    collector.checkThat(garden.getStreet(), is(GARDEN_BEAN_MOCK.getStreet()));
  }

  @Test(expected = EntityAlreadyExistsException.class)
  public void shouldLaunchExceptionWhenGardenNameAlreadyExistsOnCreate() {
    when(gardenRepository.findByName("Garden 1234")).thenReturn(Optional.of(GARDEN_MOCK_2));

    gardenService.create(GARDEN_BEAN_MOCK);
  }

  @Test(expected = MissingFieldException.class)
  public void shouldLaunchExceptionWhenGardenNameIsNullOnCreate() {
    gardenService.create(GardenDataBean.builder().name(null).build());
  }

  @Test
  public void shouldUpdateAGarden() {
    doReturn(GARDEN_MOCK).when(gardenService).get(1L);
    when(gardenRepository.findByNameExceptId("Garden 1234", 1L)).thenReturn(Optional.empty());
    when(gardenRepository.save(Mockito.any(Garden.class))).thenReturn(GARDEN_MOCK);

    gardenService.update(1L, GARDEN_BEAN_MOCK);

    ArgumentCaptor<Garden> argumentCaptor = ArgumentCaptor.forClass(Garden.class);
    verify(gardenRepository).save(argumentCaptor.capture());
    Garden garden = argumentCaptor.getValue();

    verify(gardenRepository).findByNameExceptId("Garden 1234", 1L);
    verify(gardenService).get(1L);
    collector.checkThat(garden.getId(), is(1L));
    collector.checkThat(garden.getName(), is(GARDEN_BEAN_MOCK.getName()));
    collector.checkThat(garden.getCity(), is(GARDEN_BEAN_MOCK.getCity()));
    collector.checkThat(garden.getComplement(), is(GARDEN_BEAN_MOCK.getComplement()));
    collector.checkThat(garden.getNumber(), is(GARDEN_BEAN_MOCK.getNumber()));
    collector.checkThat(garden.getZipCode(), is(GARDEN_BEAN_MOCK.getZipCode()));
    collector.checkThat(garden.getState(), is(GARDEN_BEAN_MOCK.getState()));
    collector.checkThat(garden.getStreet(), is(GARDEN_BEAN_MOCK.getStreet()));
  }

  @Test(expected = EntityAlreadyExistsException.class)
  public void shouldLaunchExceptionWhenGardenNameAlreadyExistsOnUpdate() {
    when(gardenRepository.findByNameExceptId("Garden 1234", 1L)).thenReturn(Optional.of(GARDEN_MOCK_2));

    gardenService.update(1L, GARDEN_BEAN_MOCK);
  }

  @Test(expected = MissingFieldException.class)
  public void shouldLaunchExceptionWhenGardenNameIsNullOnUpdate() {
    gardenService.update(1L, GardenDataBean.builder().name(null).build());
  }
}
