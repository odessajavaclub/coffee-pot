package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetUserResponseEvent extends ApplicationEvent {

  private final GetUserDto user;

  public GetUserResponseEvent(Object source, GetUserDto user) {
    super(source);
    this.user = user;
  }
}
