package org.odessajavaclub.user.application.port.in;

import lombok.NonNull;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

public interface ActivateUserUseCase {

    boolean activateUser(ActivateUserCommand command);

    @Value
    class ActivateUserCommand {

        @NonNull
        private final User.UserId userId;
    }
}
