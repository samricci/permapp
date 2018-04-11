package com.permapp.permappapi.dto;

import com.permapp.permappapi.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryDTO {
  private Long id;
  private String name;

  public CategoryDTO(Category category) {
    this.id = category.getId();
    this.name = category.getName();
  }
}
