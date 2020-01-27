package org.odessajavaclub.shared;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class Validating {

  private final Validator validator;

  public Validating(ValidatorFactory validatorFactory) {
    this.validator = validatorFactory.getValidator();
  }

  public Validating() {
    this(Validation.buildDefaultValidatorFactory());
  }

  public <T> void validate(T object) {
    Set<ConstraintViolation<T>> violations = validator.validate(object);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
