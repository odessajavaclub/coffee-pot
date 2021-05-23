package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.odessajavaclub.user.domain.User;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UpdateUserRequestEvent {

  private final User.UserId id;

  private final String newFirstName;

  private final String newLastName;

  private final String newEmail;

  /**
   * Constructor.
   *
   * @param id           user id
   * @param newFirstName new first name
   * @param newLastName  new last name
   * @param newEmail     new email
   */
  public UpdateUserRequestEvent(User.UserId id,
                                String newFirstName,
                                String newLastName,
                                String newEmail) {
    this.id = id;
    this.newFirstName = newFirstName;
    this.newLastName = newLastName;
    this.newEmail = newEmail;
  }
}
