package com.permapp.permappapi.service.impl;

import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;

import com.permapp.permappapi.bean.PlantDataBean;
import com.permapp.permappapi.dto.PlantDTO;
import com.permapp.permappapi.entity.Plant;
import com.permapp.permappapi.exception.EntityAlreadyExistsException;
import com.permapp.permappapi.exception.EntityNotFoundException;
import com.permapp.permappapi.repository.PlantRepository;
import com.permapp.permappapi.service.CategoryService;
import com.permapp.permappapi.service.PlantService;
import com.permapp.permappapi.validator.MandatoryFieldsValidator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class PlantServiceImpl implements PlantService {

  @Autowired
  private PlantRepository plantRepository;

  @Autowired
  private MandatoryFieldsValidator validator;

  @Autowired
  private CategoryService categoryService;

  @Override
  public Page<PlantDTO> getAll(Pageable pageable) {
    Page<Plant> plantPage = plantRepository.findAll(pageable);

    List<PlantDTO> plantDTOS = new ArrayList<>();
    if(!CollectionUtils.isEmpty(plantPage.getContent())) {
      plantDTOS = plantPage.getContent().stream().map(PlantDTO::new).collect(toList());
    }

    return new PageImpl<>(plantDTOS, pageable, plantPage.getTotalElements());
  }

  @Override
  public Plant get(Long id) {
    return plantRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Plant.class));
  }

  @Override
  public PlantDTO getDTO(Long id) {
    return new PlantDTO(get(id));
  }

  @Override
  public PlantDTO remove(Long id) {
    Plant plant = get(id);
    plantRepository.delete(plant);
    return new PlantDTO(plant);
  }

  @Override
  public PlantDTO create(PlantDataBean plantBean) {
    validateNameOnCreate(plantBean.getName());

    Plant plant = new Plant();
    copyProperties(plantBean, plant, "category");
    plant.setCategory(categoryService.get(plantBean.getCategory()));

    plant = plantRepository.save(plant);

    return new PlantDTO(plant);
  }

  @Override
  public PlantDTO update(Long id, PlantDataBean plantBean) {
    validateNameOnUpdate(id, plantBean.getName());

    Plant plant = get(id);
    copyProperties(plantBean, plant, "id", "category");
    plant.setCategory(categoryService.get(plantBean.getCategory()));

    plant = plantRepository.save(plant);
    return new PlantDTO(plant);
  }

  private void validateNameOnCreate(String name) {
    validator.validateName(name);
    hasPlantWithSameName(name);
  }

  private void validateNameOnUpdate(Long id, String name) {
    validator.validateName(name);
    hasPlantWithSameNameExceptId(id, name);
  }

  private void hasPlantWithSameName(String name) {
    if(plantRepository.findByName(name).isPresent()) {
      throw new EntityAlreadyExistsException(Plant.class);
    }
  }

  private void hasPlantWithSameNameExceptId(Long id, String name) {
    if(plantRepository.findByNameExceptId(name, id).isPresent()) {
      throw new EntityAlreadyExistsException(Plant.class);
    }
  }
}
