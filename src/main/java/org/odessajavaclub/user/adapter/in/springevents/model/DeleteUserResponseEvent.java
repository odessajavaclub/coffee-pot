package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class DeleteUserResponseEvent {

  private final boolean removed;

  public DeleteUserResponseEvent(boolean removed) {
    this.removed = removed;
  }
}
