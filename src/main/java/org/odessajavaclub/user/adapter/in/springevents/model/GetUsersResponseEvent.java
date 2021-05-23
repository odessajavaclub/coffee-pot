package org.odessajavaclub.user.adapter.in.springevents.model;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class GetUsersResponseEvent {

  private final List<GetUserDto> users;

  public GetUsersResponseEvent(List<GetUserDto> users) {
    this.users = users;
  }
}
