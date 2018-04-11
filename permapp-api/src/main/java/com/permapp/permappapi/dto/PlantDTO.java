package com.permapp.permappapi.dto;

import com.permapp.permappapi.entity.Category;
import com.permapp.permappapi.entity.Plant;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PlantDTO {

  private Long id;
  private String name;
  private String name2;
  private String name3;
  private String name4;
  private String name5;
  private String estract;
  private String spaceL;
  private String spaceH;
  private String timeHarvest;
  private String timeRegrowth;
  private String timeLifeCicle;
  private BigDecimal mudaPrice;
  private BigDecimal salePrice;
  private BigDecimal seedPrice;
  private String info;
  private Integer shading;
  //imagem 1 e 2
  private Category category;

  public PlantDTO(Plant plant) {
    this.id = plant.getId();
    this.name = plant.getName();
    this.name2 = plant.getName2();
    this.name3 = plant.getName3();
    this.name4 = plant.getName4();
    this.name5 = plant.getName5();
    this.estract = plant.getEstract();
    this.spaceL = plant.getSpaceL();
    this.spaceH = plant.getSpaceH();
    this.timeHarvest = plant.getTimeHarvest();
    this.timeRegrowth = plant.getTimeRegrowth();
    this.timeLifeCicle = plant.getTimeLifeCicle();
    this.mudaPrice = plant.getMudaPrice();
    this.seedPrice = plant.getSeedPrice();
    this.salePrice = plant.getSalePrice();
    this.info = plant.getInfo();
    this.shading = plant.getShading();
    this.category = plant.getCategory();
  }
}
