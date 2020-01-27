package org.odessajavaclub.user.adapter.in.springevents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDto {

  private long id;

  private String firstName;

  private String lastName;

  private String email;

  private UserSpringEventRole role;

  private boolean active;
}
