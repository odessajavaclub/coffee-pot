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

  /**
   * Validates {@code object} and throws {@link ConstraintViolationException} if it is invalid.
   *
   * @param object input object to be validated
   * @param <T>    type of input object
   * @throws ConstraintViolationException if input object is invalid
   */
  public <T> void validate(T object) {
    Set<ConstraintViolation<T>> violations = validator.validate(object);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
