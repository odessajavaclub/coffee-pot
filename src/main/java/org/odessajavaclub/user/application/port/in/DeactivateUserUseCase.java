package org.odessajavaclub.user.application.port.in;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.domain.User;

public interface DeactivateUserUseCase {

  Optional<User> deactivateUser(DeactivateUserCommand command);

  @Data
  @Builder
  @RequiredArgsConstructor
  class DeactivateUserCommand {

    @NotNull
    private final User.UserId userId;
  }
}
