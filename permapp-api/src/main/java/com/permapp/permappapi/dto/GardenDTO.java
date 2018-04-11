package com.permapp.permappapi.dto;

import com.permapp.permappapi.entity.Garden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GardenDTO {

  private Long id;
  private String name;
  private String street;
  private String number;
  private String complement;
  private String zipCode;
  private String city;
  private String state;

  public GardenDTO(Garden garden) {
    this.id = garden.getId();
    this.name = garden.getName();
    this.street = garden.getStreet();
    this.number = garden.getNumber();
    this.complement = garden.getComplement();
    this.zipCode = garden.getZipCode();
    this.city = garden.getCity();
    this.state = garden.getState();
  }
}
