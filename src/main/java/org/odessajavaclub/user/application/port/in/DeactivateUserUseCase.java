package org.odessajavaclub.user.application.port.in;

import lombok.NonNull;
import lombok.Value;
import org.odessajavaclub.user.domain.User.UserId;

public interface DeactivateUserUseCase {

    boolean deactivateUser(DeactivateUserCommand command);

    @Value
    class DeactivateUserCommand {

        @NonNull
        private final UserId userId;
    }
}
