package org.odessajavaclub.user.adapter.in.rest;

import lombok.Data;

@Data
class GetUserDto {

  private long id;

  private String firstName;

  private String lastName;

  private String email;

  private String role;

  private boolean active;
}
