package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetUserRequestEvent extends ApplicationEvent {

  private final User.UserId id;

  public GetUserRequestEvent(Object source, User.UserId id) {
    super(source);
    this.id = id;
  }
}
