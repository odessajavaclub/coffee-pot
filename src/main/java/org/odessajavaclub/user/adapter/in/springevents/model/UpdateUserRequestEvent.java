package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UpdateUserRequestEvent extends ApplicationEvent {

  private User.UserId id;

  private String newFirstName;

  private String newLastName;

  private String newEmail;

  public UpdateUserRequestEvent(Object source,
                                User.UserId id,
                                String newFirstName,
                                String newLastName,
                                String newEmail) {
    super(source);
    this.id = id;
    this.newFirstName = newFirstName;
    this.newLastName = newLastName;
    this.newEmail = newEmail;
  }
}
