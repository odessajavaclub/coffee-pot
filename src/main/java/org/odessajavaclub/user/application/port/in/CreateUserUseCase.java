package org.odessajavaclub.user.application.port.in;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;

public interface CreateUserUseCase {

  User createActiveUser(CreateUserCommand command);

  User createInactiveUser(CreateUserCommand command);

  @Value
  @Builder
  @AllArgsConstructor
  class CreateUserCommand {

    @NotBlank
    private final String firstName;

    @NotBlank
    private final String lastName;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final String password;

    @NotNull
    private final UserRole role;
  }
}
