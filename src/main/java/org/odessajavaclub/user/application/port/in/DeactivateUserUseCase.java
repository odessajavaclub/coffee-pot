package org.odessajavaclub.user.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.user.domain.User;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface DeactivateUserUseCase {

    Optional<User> deactivateUser(DeactivateUserCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class DeactivateUserCommand extends SelfValidating<DeactivateUserCommand> {

        @NotNull
        private final User.UserId userId;

        public DeactivateUserCommand(User.UserId userId) {
            this.userId = userId;
            this.validateSelf();
        }
    }
}
