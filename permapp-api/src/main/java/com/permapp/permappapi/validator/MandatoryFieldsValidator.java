package com.permapp.permappapi.validator;

import com.permapp.permappapi.exception.MissingFieldException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MandatoryFieldsValidator {

  public void validateName(String name) {
    if(StringUtils.isEmpty(name)) {
      throw new MissingFieldException("Name");
    }
  }
}
