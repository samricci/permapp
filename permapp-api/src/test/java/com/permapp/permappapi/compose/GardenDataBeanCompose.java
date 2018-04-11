package com.permapp.permappapi.compose;

import com.permapp.permappapi.bean.GardenDataBean;

public class GardenDataBeanCompose {
  public static final GardenDataBean.GardenDataBeanBuilder getGardenDataBeanDefault() {
    return GardenDataBean.builder()
        .name("Garden 1234")
        .city("São Paulo")
        .state("SP")
        .street("Rua José Bonifácio")
        .number("90234")
        .complement("Apartamento 9034")
        .zipCode("02929-399");
  }
}
