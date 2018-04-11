package com.permapp.permappapi.controller;

import static java.lang.Integer.MAX_VALUE;

import com.permapp.permappapi.bean.GardenDataBean;
import com.permapp.permappapi.dto.GardenDTO;
import com.permapp.permappapi.service.GardenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hortas")
public class GardenController {

  @Autowired
  private GardenService service;

  @GetMapping
  private Page<GardenDTO> getAll(@PageableDefault(size = MAX_VALUE) Pageable pageable) {
    return service.getAll(pageable);
  }

  @GetMapping("{id}")
  private GardenDTO get(@PathVariable Long id) {
    return service.getDTO(id);
  }

  @DeleteMapping("{id}")
  private GardenDTO remove(@PathVariable Long id) {
    return service.remove(id);
  }

  @PostMapping
  private GardenDTO create(@RequestBody @Validated GardenDataBean gardenBean) {
    return service.create(gardenBean);
  }

  @PutMapping("{id}")
  private GardenDTO update(
      @PathVariable Long id,
      @RequestBody @Validated GardenDataBean gardenBean) {
    return service.update(id, gardenBean);
  }
}
