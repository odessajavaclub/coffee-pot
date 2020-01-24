package org.odessajavaclub.user.application.port.in;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

public interface DeactivateUserUseCase {

  Optional<User> deactivateUser(DeactivateUserCommand command);

  @Value
  @RequiredArgsConstructor
  @Builder
  class DeactivateUserCommand {

    @NotNull
    private final User.UserId id;
  }
}
