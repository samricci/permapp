package com.permapp.permappapi.bean;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PlantDataBean {

  @NotNull
  private Long category;

  @NotNull
  @Size(min = 2, max = 50)
  private String name;

  @Size(min = 2, max = 50)
  private String name2;

  @Size(min = 2, max = 50)
  private String name3;

  @Size(min = 2, max = 50)
  private String name4;

  @Size(min = 2, max = 50)
  private String name5;

  @Size(min = 2, max = 15)
  private String estract;

  @Size(min = 2, max = 15)
  private String spaceL;

  @Size(min = 2, max = 15)
  private String spaceH;

  @Size(min = 2, max = 20)
  private String timeHarvest;

  @Size(min = 2, max = 20)
  private String timeRegrowth;

  @Size(min = 2, max = 20)
  private String timeLifeCicle;

  @Size(max = 500)
  private String info;

  private BigDecimal mudaPrice;

  private BigDecimal salePrice;

  private BigDecimal seedPrice;

  private Integer shading;

  //imagem 1 e 2
}
