package org.odessajavaclub.user.application.port.in;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;

public interface CreateUserUseCase {

  User createActiveUser(CreateUserCommand command);

  User createInactiveUser(CreateUserCommand command);

  @Value
  @EqualsAndHashCode(callSuper = false)
  class CreateUserCommand extends SelfValidating<CreateUserCommand> {

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

    public CreateUserCommand(String firstName,
                             String lastName,
                             String email,
                             String password,
                             UserRole role) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.role = role;
      this.validateSelf();
    }
  }
}
