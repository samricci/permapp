package com.permapp.permappapi.compose;

import com.permapp.permappapi.entity.Garden;

public class GardenCompose {
  public static Garden.GardenBuilder getGardenDefault() {
    return Garden.builder()
        .id(1L)
        .name("Horta 1")
        .street("Rua José Ferreira")
        .number("123")
        .complement("Fundos")
        .zipCode("12345-029")
        .city("Petrópolis")
        .state("Rio de Janeiro");
  }
}
