package org.odessajavaclub.user.application.port.in;

import lombok.NonNull;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

public interface DeactivateUserUseCase {

    void deactivateUser(DeactivateUserCommand command);

    @Value
    class DeactivateUserCommand {

        @NonNull
        private final User.UserId userId;
    }
}
