package com.permapp.permappapi;

import static com.permapp.permappapi.constants.ExceptionsConstants.ENTITY_ALREADY_EXISTS_PROPERTY;
import static com.permapp.permappapi.constants.ExceptionsConstants.ENTITY_NOT_FOUND_PROPERTY;
import static com.permapp.permappapi.constants.ExceptionsConstants.ENUM_NOT_FOUND_PROPERTY;
import static com.permapp.permappapi.constants.ExceptionsConstants.MISSING_FIELD_PROPERTY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.permapp.permappapi.exception.EntityAlreadyExistsException;
import com.permapp.permappapi.exception.EntityNotFoundException;
import com.permapp.permappapi.exception.EnumNotFoundException;
import com.permapp.permappapi.exception.MissingFieldException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PermappExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler( {EntityNotFoundException.class} )
  public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
    String errorMessage = getMessageFromProperty(ENTITY_NOT_FOUND_PROPERTY) + ex.getEntity().getSimpleName();
    return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), ex.getStatus(), request);
  }

  @ExceptionHandler( {MissingFieldException.class} )
  public ResponseEntity<Object> handleMissingFieldException(MissingFieldException ex, WebRequest request) {
    String errorMessage = getMessageFromProperty(MISSING_FIELD_PROPERTY) + ex.getField();
    return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), ex.getStatus(), request);
  }

  @ExceptionHandler( {EntityAlreadyExistsException.class} )
  public ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest request) {
    String errorMessage = getMessageFromProperty(ENTITY_ALREADY_EXISTS_PROPERTY) + ex.getEntity().getSimpleName();
    return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), ex.getStatus(), request);
  }

  @ExceptionHandler( {EnumNotFoundException.class})
  public ResponseEntity<Object> handleEnumNotFoundException(EnumNotFoundException ex, WebRequest request) {
    String errorMessage = getMessageFromProperty(ENUM_NOT_FOUND_PROPERTY) + ex.getEntity().getSimpleName();
    return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), ex.getStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(ex, createErrorList(ex.getBindingResult()), headers, BAD_REQUEST,
        request);
  }

  private String getMessageFromProperty(String propertyKey) {
    return messageSource.getMessage(propertyKey, null, LocaleContextHolder.getLocale());
  }

  private List<String> createErrorList(BindingResult bindingResult) {
    List<String> errors = new ArrayList<>();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      errors.add(messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()));
    }

    return errors;
  }
}
