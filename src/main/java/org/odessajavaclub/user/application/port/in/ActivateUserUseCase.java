package org.odessajavaclub.user.application.port.in;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

public interface ActivateUserUseCase {

  Optional<User> activateUser(ActivateUserCommand command);

  @Value
  class ActivateUserCommand {

    @NotNull
    private final User.UserId userId;
  }
}
