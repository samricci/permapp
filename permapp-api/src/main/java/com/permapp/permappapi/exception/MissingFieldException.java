package com.permapp.permappapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MissingFieldException extends RuntimeException {
  private HttpStatus status;
  private String field;

  public MissingFieldException(String field) {
    super();
    this.field = field;
    this.status = HttpStatus.BAD_REQUEST;
  }
}
