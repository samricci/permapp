package com.permapp.permappapi.compose;

import com.permapp.permappapi.entity.Category;

public class CategoryCompose {

  public static Category.CategoryBuilder getCategoryDefault() {
    return Category.builder()
        .id(1L)
        .name("Categoria 1");
  }
}
