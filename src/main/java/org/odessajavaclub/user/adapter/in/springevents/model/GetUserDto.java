package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.Value;

@Value
public class GetUserDto {

  private long id;

  private String firstName;

  private String lastName;

  private String email;

  private String role;

  private boolean active;
}
