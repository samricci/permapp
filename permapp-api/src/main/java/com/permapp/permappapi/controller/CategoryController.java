package com.permapp.permappapi.controller;

import static java.lang.Integer.MAX_VALUE;

import com.permapp.permappapi.bean.CategoryDataBean;
import com.permapp.permappapi.dto.CategoryDTO;
import com.permapp.permappapi.service.CategoryService;
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
@RequestMapping("/categorias")
public class CategoryController {

  @Autowired
  private CategoryService service;

  @GetMapping
  public Page<CategoryDTO> getAll(@PageableDefault(size = MAX_VALUE) Pageable pageable) {
    return service.getAll(pageable);
  }

  @GetMapping("{id}")
  public CategoryDTO get(@PathVariable Long id) {
    return service.getDTO(id);
  }

  @DeleteMapping("{id}")
  public CategoryDTO remove(@PathVariable Long id) {
    return service.remove(id);
  }

  @PutMapping("{id}")
  public CategoryDTO update(
      @PathVariable Long id,
      @RequestBody @Validated CategoryDataBean categoryBean) {
    return service.update(id, categoryBean);
  }

  @PostMapping
  public CategoryDTO create(@RequestBody @Validated CategoryDataBean categoryBean) {
    return service.create(categoryBean);
  }
}
