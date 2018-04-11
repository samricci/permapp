package com.permapp.permappapi.service;

import com.permapp.permappapi.bean.CategoryDataBean;
import com.permapp.permappapi.dto.CategoryDTO;
import com.permapp.permappapi.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
  Page<CategoryDTO> getAll(Pageable pageable);

  Category get(Long id);

  CategoryDTO getDTO(Long id);

  CategoryDTO remove(Long id);

  CategoryDTO create(CategoryDataBean categoryDataBean);

  CategoryDTO update(Long id, CategoryDataBean categoryBean);
}
