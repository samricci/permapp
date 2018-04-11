package com.permapp.permappapi.service;

import com.permapp.permappapi.bean.PlantDataBean;
import com.permapp.permappapi.dto.PlantDTO;
import com.permapp.permappapi.entity.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlantService {

  Page<PlantDTO> getAll(Pageable pageable);

  Plant get(Long id);

  PlantDTO getDTO(Long id);

  PlantDTO remove(Long id);

  PlantDTO create(PlantDataBean plantDataBean);

  PlantDTO update(Long id, PlantDataBean plantDataBean);
}
