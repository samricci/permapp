package com.permapp.permappapi.compose;

import static com.permapp.permappapi.compose.CategoryCompose.getCategoryDefault;

import com.permapp.permappapi.entity.Category;
import com.permapp.permappapi.entity.Plant;
import java.math.BigDecimal;

public class PlantCompose {

  private static final Category CATEGORY_MOCK = getCategoryDefault().build();

  public static Plant.PlantBuilder getPlantDefault() {
    return Plant.builder()
        .id(1L)
        .name("Alface")
        .name2("Alface 2")
        .name3("Alface 3")
        .name4("Alface 4")
        .name5("Alface 5")
        .category(CATEGORY_MOCK)
        .estract("Estract 1")
        .info("Informações sobre Alface")
        .mudaPrice(BigDecimal.valueOf(0.50))
        .salePrice(BigDecimal.valueOf(1.00))
        .seedPrice(BigDecimal.valueOf(0.20))
        .shading(18)
        .spaceH("blas cm")
        .spaceL("blas m")
        .timeHarvest("10 dias")
        .timeLifeCicle("90 dias")
        .timeRegrowth("3 meses");
  }
}
