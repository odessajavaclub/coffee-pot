package org.odessajavaclub.user.adapter.in.springevents.model;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetUsersResponseEvent extends ApplicationEvent {

  private final List<GetUserDto> users;

  public GetUsersResponseEvent(Object source, List<GetUserDto> users) {
    super(source);
    this.users = users;
  }
}
