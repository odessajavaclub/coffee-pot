package org.odessajavaclub.user.adapter.in.springevents.model;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class UpdateUserDto {

  private String firstName;

  private String lastName;

  @Email
  private String email;
}
