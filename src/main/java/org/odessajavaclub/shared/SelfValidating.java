package org.odessajavaclub.shared;

public abstract class SelfValidating<T> {

  private final Validating validating;

  protected SelfValidating() {
    this.validating = new Validating();
  }

  protected void validateSelf() {
    validating.validate(this);
  }
}
