package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.odessajavaclub.user.domain.User;

@Getter
@EqualsAndHashCode(callSuper = false)
public class DeleteUserRequestEvent {

  private final User.UserId id;

  public DeleteUserRequestEvent(User.UserId id) {
    this.id = id;
  }
}
