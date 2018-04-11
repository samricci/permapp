package com.permapp.permappapi.service.impl;

import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;

import com.permapp.permappapi.bean.CategoryDataBean;
import com.permapp.permappapi.dto.CategoryDTO;
import com.permapp.permappapi.entity.Category;
import com.permapp.permappapi.exception.EntityAlreadyExistsException;
import com.permapp.permappapi.exception.EntityNotFoundException;
import com.permapp.permappapi.repository.CategoryRepository;
import com.permapp.permappapi.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private MandatoryFieldsValidator validator;

  @Override
  public Page<CategoryDTO> getAll(Pageable pageable) {
    Page<Category> categories = categoryRepository.findAll(pageable);

    List<CategoryDTO> dtos = new ArrayList<>();
    if(!CollectionUtils.isEmpty(categories.getContent())) {
      dtos = categories.stream().map(CategoryDTO::new).collect(toList());
    }

    return new PageImpl<>(dtos, pageable, categories.getTotalElements());
  }

  @Override
  public Category get(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Category.class));
  }

  @Override
  public CategoryDTO getDTO(Long id) {
    return new CategoryDTO(get(id));
  }

  @Override
  public CategoryDTO remove(Long id) {
    Category category = get(id);
    categoryRepository.delete(category);
    return new CategoryDTO(category);
  }

  @Override
  public CategoryDTO create(CategoryDataBean categoryDataBean) {
    validateNameCreate(categoryDataBean.getName());

    Category category = new Category();
    copyProperties(categoryDataBean, category, "id");
    category = categoryRepository.save(category);

    return new CategoryDTO(category);
  }

  @Override
  public CategoryDTO update(Long id, CategoryDataBean categoryBean) {
    validateNameUpdate(id, categoryBean.getName());

    Category category = get(id);
    copyProperties(categoryBean, category, "id");
    category = categoryRepository.save(category);

    return new CategoryDTO(category);
  }

  private void hasCategoryWithSameName(String name) {
    if(categoryRepository.findByName(name).isPresent()) {
      throw new EntityAlreadyExistsException(Category.class);
    }
  }

  private void hasCategoryWithSameNameExceptsID(String name, Long id) {
    if(categoryRepository.findByNameExceptId(name, id).isPresent()) {
      throw new EntityAlreadyExistsException(Category.class);
    }
  }

  private void validateNameUpdate(Long id, String name) {
    validator.validateName(name);
    hasCategoryWithSameNameExceptsID(name, id);
  }

  public void validateNameCreate(String name) {
    validator.validateName(name);
    hasCategoryWithSameName(name);
  }
}