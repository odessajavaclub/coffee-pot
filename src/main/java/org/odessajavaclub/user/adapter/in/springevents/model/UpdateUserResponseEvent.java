package org.odessajavaclub.user.adapter.in.springevents.model;

import java.util.Optional;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = false)
public class UpdateUserResponseEvent extends ApplicationEvent {

  private final GetUserDto updatedUser;

  public UpdateUserResponseEvent(Object source, GetUserDto updatedUser) {
    super(source);
    this.updatedUser = updatedUser;
  }

  public Optional<GetUserDto> getUpdatedUser() {
    return Optional.ofNullable(updatedUser);
  }
}
