package com.permapp.permappapi.controller;

import static java.lang.Integer.MAX_VALUE;

import com.permapp.permappapi.bean.PlantDataBean;
import com.permapp.permappapi.dto.PlantDTO;
import com.permapp.permappapi.service.PlantService;
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
@RequestMapping("plantas")
public class PlantController {

  @Autowired
  private PlantService plantService;

  @GetMapping
  public Page<PlantDTO> getAll(@PageableDefault(size = MAX_VALUE) Pageable pageable) {
    return plantService.getAll(pageable);
  }

  @GetMapping("{id}")
  public PlantDTO get(@PathVariable Long id) {
    return plantService.getDTO(id);
  }

  @DeleteMapping("{id}")
  public PlantDTO remove(@PathVariable Long id) {
    return plantService.remove(id);
  }

  @PostMapping
  public PlantDTO create(@RequestBody @Validated PlantDataBean plantBean) {
    return plantService.create(plantBean);
  }

  @PutMapping
  public PlantDTO update(
      @PathVariable Long id,
      @RequestBody @Validated PlantDataBean plantBean) {
    return plantService.update(id, plantBean);
  }
}
