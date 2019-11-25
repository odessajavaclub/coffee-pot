package org.odessajavaclub.user.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.user.domain.User;

import javax.validation.constraints.NotNull;

public interface ActivateUserUseCase {

    boolean activateUser(ActivateUserCommand command);

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
