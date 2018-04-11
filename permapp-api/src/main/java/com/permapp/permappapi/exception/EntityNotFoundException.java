package com.permapp.permappapi.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends RuntimeException {
  private HttpStatus status;
  private Class entity;

  public EntityNotFoundException(Class entity) {
    super();
    this.entity = entity;
    this.status = NOT_FOUND;
  }
}
