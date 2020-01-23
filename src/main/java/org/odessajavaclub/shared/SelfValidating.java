package org.odessajavaclub.shared;

import javax.validation.ValidatorFactory;

public abstract class SelfValidating<T> {

  private final Validating validating;

  protected SelfValidating(ValidatorFactory validatorFactory) {
    this.validating = new Validating(validatorFactory);
  }

  protected SelfValidating() {
    this.validating = new Validating();
  }

  protected void validateSelf() {
    validating.validate(this);
  }
}
