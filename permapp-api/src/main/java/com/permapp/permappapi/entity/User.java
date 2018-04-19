package com.permapp.permappapi.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class User {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "NOME", length = 50)
  private String name;

  @Column(name = "EMAIL", length = 50, nullable = false)
  private String email;

  @Column(name = "SENHA", length = 59, nullable = false)
  private String password;

  @ManyToMany
  @JoinTable(name = "usuario_role",
    joinColumns = { @JoinColumn(name = "usuario_id") },
    inverseJoinColumns = { @JoinColumn(name = "role_id") })
  private Set<Role> roles;
}
