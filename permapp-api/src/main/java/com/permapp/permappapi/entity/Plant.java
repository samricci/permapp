package com.permapp.permappapi.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "plantas")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Plant {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "NOME", length = 50, nullable = false)
  private String name;

  @Column(name = "NOME2", length = 50)
  private String name2;

  @Column(name = "NOME3", length = 50)
  private String name3;

  @Column(name = "NOME4", length = 50)
  private String name4;

  @Column(name = "NOME5", length = 50)
  private String name5;

  @Column(name = "ESTRATO", length = 15)
  private String estract;

  @Column(name = "ESPACOL", length = 15)
  private String spaceL;

  @Column(name = "ESPACOH", length = 15)
  private String spaceH;

  @Column(name = "TEMPO_COLHEITA", length = 20)
  private String timeHarvest;

  @Column(name = "TEMPO_REBROTA", length = 20)
  private String timeRegrowth;

  @Column(name = "TEMPO_VIDA", length = 20)
  private String timeLifeCicle;

  @Column(name = "VALOR_MUDA", precision = 2)
  private BigDecimal mudaPrice;

  @Column(name = "VALOR_VENDA", precision = 2)
  private BigDecimal salePrice;

  @Column(name = "VALOR_SEMENTE", precision = 2)
  private BigDecimal seedPrice;

  @Column(name = "INFORMACOES", length = 500)
  private String info;

  @Column(name = "SOMBREAMENTO")
  private Integer shading;

  //imagem 1 e 2

  @ManyToOne
  @JoinColumn(name = "CATEGORIA_ID")
  private Category category;
}
