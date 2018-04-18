package com.permapp.permappapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class User {

  private Long id;

  @Column(name = "EMAIL", length = 50, nullable = false)
  private String name;

  @Column(name = "EMAIL", length = 50, nullable = false)
  private String email;

  @Column(name = "SENHA", length = 59, nullable = false)
  private String password;
}
