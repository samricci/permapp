package com.permapp.permappapi.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {
  private HttpStatus status;
  private Class entity;

  public EntityAlreadyExistsException(Class entity) {
    super();
    this.status = CONFLICT;
    this.entity = entity;
  }
}
