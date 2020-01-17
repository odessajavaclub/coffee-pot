package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CreateActiveUserResponseEvent extends ApplicationEvent {

  private final GetUserDto createdUser;

  public CreateActiveUserResponseEvent(Object source, GetUserDto createdUser) {
    super(source);
    this.createdUser = createdUser;
  }
}
