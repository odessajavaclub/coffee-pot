package org.odessajavaclub.user.application.port.in;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

public interface UpdateUserUseCase {

  Optional<User> updateUser(UpdateUserCommand command);

  @Value
  @RequiredArgsConstructor
  @Builder
  class UpdateUserCommand {

    @NotNull
    private final User.UserId id;

    private String newFirstName;

    private String newLastName;

    private String newEmail;
  }
}
