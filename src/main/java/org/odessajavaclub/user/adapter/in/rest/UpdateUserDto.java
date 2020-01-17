package org.odessajavaclub.user.adapter.in.rest;

import javax.validation.constraints.Email;
import lombok.Value;

@Value
class UpdateUserDto {

  private String firstName;

  private String lastName;

  @Email
  private String email;
}
