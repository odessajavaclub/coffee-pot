package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetUserResponseEvent {

  private final GetUserDto user;

  public GetUserResponseEvent(GetUserDto user) {
    this.user = user;
  }
}
