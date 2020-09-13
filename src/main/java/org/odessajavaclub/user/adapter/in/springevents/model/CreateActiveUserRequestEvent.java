package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
// since Spring 4.2, we can also publish objects as an event without extending ApplicationEvent
public class CreateActiveUserRequestEvent {

  private final String firstName;

  private final String lastName;

  private final String email;

  private final String password;

  private final UserSpringEventRole role;

  /**
   * Constructor.
   *
   * @param firstName first name
   * @param lastName  last name
   * @param email     email
   * @param password  password
   * @param role      role
   */
  public CreateActiveUserRequestEvent(String firstName,
                                      String lastName,
                                      String email,
                                      String password,
                                      UserSpringEventRole role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
  }
}
