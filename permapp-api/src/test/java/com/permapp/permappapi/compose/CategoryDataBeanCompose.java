package com.permapp.permappapi.compose;

import com.permapp.permappapi.bean.CategoryDataBean;

public class CategoryDataBeanCompose {
  public static final CategoryDataBean.CategoryDataBeanBuilder getDefaultCategoryDataBean() {
    return CategoryDataBean.builder()
        .name("Categoria 1234");
  }
}
