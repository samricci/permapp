package com.permapp.permappapi.service;

import com.permapp.permappapi.bean.GardenDataBean;
import com.permapp.permappapi.dto.GardenDTO;
import com.permapp.permappapi.entity.Garden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GardenService {
  Page<GardenDTO> getAll(Pageable pageable);

  Garden get(Long id);

  GardenDTO getDTO(Long id);

  GardenDTO remove(Long id);

  GardenDTO create(GardenDataBean gardenDataBean);

  GardenDTO update(Long id, GardenDataBean gardenDataBean);
}
