package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CreateActiveUserResponseEvent {

  private final GetUserDto createdUser;

  public CreateActiveUserResponseEvent(GetUserDto createdUser) {
    this.createdUser = createdUser;
  }
}
