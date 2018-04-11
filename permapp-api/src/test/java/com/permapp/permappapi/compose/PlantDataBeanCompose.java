package com.permapp.permappapi.compose;

import com.permapp.permappapi.bean.PlantDataBean;
import java.math.BigDecimal;

public class PlantDataBeanCompose {
  public static PlantDataBean.PlantDataBeanBuilder getDefaultPlantDataBean() {
    return PlantDataBean.builder()
        .name("Nome Planta 1")
        .name2("Nome Planta 2")
        .name3("Nome Planta 3")
        .name4("Nome Planta 4")
        .name5("Nome Planta 5")
        .category(1L)
        .estract("Estrato 1")
        .info("Informações gerais e adicionais")
        .mudaPrice(BigDecimal.valueOf(1.0))
        .salePrice(BigDecimal.valueOf(2.0))
        .seedPrice(BigDecimal.valueOf(0.31))
        .shading(27)
        .spaceH("27 cm")
        .spaceL("89 cm")
        .timeHarvest("5 meses")
        .timeLifeCicle("10 meses")
        .timeRegrowth("2 meses");
  }

  public static PlantDataBean.PlantDataBeanBuilder getPlantDataBeanWithNullName() {
    return PlantDataBean.builder()
        .name(null)
        .name2("Nome Planta 2")
        .name3("Nome Planta 3")
        .name4("Nome Planta 4")
        .name5("Nome Planta 5")
        .category(1L)
        .estract("Estrato 1")
        .info("Informações gerais e adicionais")
        .mudaPrice(BigDecimal.valueOf(1.0))
        .salePrice(BigDecimal.valueOf(2.0))
        .seedPrice(BigDecimal.valueOf(0.31))
        .shading(27)
        .spaceH("27 cm")
        .spaceL("89 cm")
        .timeHarvest("5 meses")
        .timeLifeCicle("10 meses")
        .timeRegrowth("2 meses");
  }
}
