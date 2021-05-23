package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.odessajavaclub.user.domain.User;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetUserRequestEvent {

  private final User.UserId id;

  public GetUserRequestEvent(User.UserId id) {
    this.id = id;
  }
}
