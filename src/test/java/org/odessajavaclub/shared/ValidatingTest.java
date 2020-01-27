package org.odessajavaclub.shared;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidatingTest {

  private Validating validating;

  @BeforeEach
  void setUp() {
    validating = new Validating();
  }

  @Test
  void validateShouldNotThrowExceptionOnValidException() {
    NullableObject nullableObject = new NullableObject();
    nullableObject.setObject(null);

    validating.validate(nullableObject);
  }

  @Test
  void validateShouldThrowException() {
    NotNullableObject notNullableObject = new NotNullableObject();
    notNullableObject.setObject(null);

    assertThrows(ConstraintViolationException.class, () -> validating.validate(notNullableObject));
  }

  @Data
  private static class NotNullableObject {

    @NotNull
    private Object object;
  }

  @Data
  private static class NullableObject {

    private Object object;
  }
}
