package org.odessajavaclub.user.adapter.in.springevents.model;

import java.util.Optional;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class UpdateUserResponseEvent {

  private final GetUserDto updatedUser;

  public UpdateUserResponseEvent(GetUserDto updatedUser) {
    this.updatedUser = updatedUser;
  }

  public Optional<GetUserDto> getUpdatedUser() {
    return Optional.ofNullable(updatedUser);
  }
}
