package org.odessajavaclub.user.application.port.in;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.user.domain.User;

public interface ActivateUserUseCase {

  Optional<User> activateUser(ActivateUserCommand command);

  @Value
  @EqualsAndHashCode(callSuper = false)
  class ActivateUserCommand extends SelfValidating<ActivateUserCommand> {

    @NotNull
    private final User.UserId userId;

    public ActivateUserCommand(User.UserId userId) {
      this.userId = userId;
      this.validateSelf();
    }
  }
}
