package org.odessajavaclub.user.domain;

import java.util.Optional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class User {

  private UserId id;

  @NotBlank
  @Size(max = 100)
  private String firstName;

  @NotBlank
  @Size(max = 100)
  private String lastName;

  @Email
  private String email;

  @NotBlank
  private String password;

  @NotNull
  private UserRole role;

  private boolean active;

  public Optional<UserId> getIdOptional() {
    return Optional.ofNullable(id);
  }

  @Value
  public static class UserId {

    private long value;
  }
}
