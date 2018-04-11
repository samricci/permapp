package com.permapp.permappapi.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "hortas")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Garden {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "NOME", length = 20, nullable = false)
  private String name;

  @Column(name = "LOGRADOURO", length = 50)
  private String street;

  @Column(name = "NUMERO", length = 10)
  private String number;

  @Column(name = "COMPLEMENTO", length = 20)
  private String complement;

  @Column(name = "CEP", length = 9)
  private String zipCode;

  @Column(name = "CIDADE", length = 20)
  private String city;

  @Column(name = "ESTADO", length = 20)
  private String state;
}
