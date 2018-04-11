package com.permapp.permappapi.service.impl;

import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;

import com.permapp.permappapi.bean.GardenDataBean;
import com.permapp.permappapi.dto.GardenDTO;
import com.permapp.permappapi.entity.Garden;
import com.permapp.permappapi.exception.EntityAlreadyExistsException;
import com.permapp.permappapi.exception.EntityNotFoundException;
import com.permapp.permappapi.repository.GardenRepository;
import com.permapp.permappapi.service.GardenService;
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
public class GardenServiceImpl implements GardenService {

  @Autowired
  private GardenRepository gardenRepository;

  @Autowired
  private MandatoryFieldsValidator validator;

  @Override
  public Page<GardenDTO> getAll(Pageable pageable) {
    Page<Garden> gardenList = gardenRepository.findAll(pageable);

    List<GardenDTO> gardenDTOS = new ArrayList<>();
    if(!CollectionUtils.isEmpty(gardenList.getContent())) {
      gardenDTOS = gardenList.stream().map(GardenDTO::new).collect(toList());
    }

    return new PageImpl<>(gardenDTOS, pageable, gardenList.getTotalElements());
  }

  @Override
  public Garden get(Long id) {
    return gardenRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Garden.class));
  }

  @Override
  public GardenDTO getDTO(Long id) {
    return new GardenDTO(get(id));
  }

  @Override
  public GardenDTO remove(Long id) {
    Garden garden = get(id);
    gardenRepository.delete(garden);
    return new GardenDTO(garden);
  }

  @Override
  public GardenDTO create(GardenDataBean gardenDataBean) {
    validateNameCreate(gardenDataBean.getName());

    Garden garden = new Garden();
    copyProperties(gardenDataBean, garden);
    garden = gardenRepository.save(garden);

    return new GardenDTO(garden);
  }

  @Override
  public GardenDTO update(Long id, GardenDataBean gardenBean) {
    validateNameUpdate(id, gardenBean.getName());

    Garden garden = get(id);
    copyProperties(gardenBean, garden);
    garden = gardenRepository.save(garden);

    return new GardenDTO(garden);
  }

  private void validateNameCreate(String name) {
    validator.validateName(name);
    hasGardenWithSameName(name);
  }

  private void validateNameUpdate(Long id, String name) {
    validator.validateName(name);
    hasGardenWithSameNameExceptId(name, id);
  }

  private void hasGardenWithSameName(String name) {
    if(gardenRepository.findByName(name).isPresent()) {
      throw new EntityAlreadyExistsException(Garden.class);
    }
  }

  private void hasGardenWithSameNameExceptId(String name, Long id) {
    if(gardenRepository.findByNameExceptId(name, id).isPresent()) {
      throw new EntityAlreadyExistsException(Garden.class);
    }
  }
}
