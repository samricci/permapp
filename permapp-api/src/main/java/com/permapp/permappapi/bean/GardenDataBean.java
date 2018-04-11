package com.permapp.permappapi.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GardenDataBean {

  @NotNull
  @Size(min = 2, max = 20)
  private String name;

  @Size(min = 2, max = 50)
  private String street;

  @Size(min = 1, max = 10)
  private String number;

  @Size(min = 2, max = 20)
  private String complement;

  @Size(min = 8, max = 9)
  private String zipCode;

  @Size(min = 2, max = 20)
  private String city;

  @Size(min = 2, max = 20)
  private String state;
}
