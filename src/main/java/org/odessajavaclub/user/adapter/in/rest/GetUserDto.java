package org.odessajavaclub.user.adapter.in.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class GetUserDto {

  private long id;

  private String firstName;

  private String lastName;

  private String email;

  private String role;

  private boolean active;
}
