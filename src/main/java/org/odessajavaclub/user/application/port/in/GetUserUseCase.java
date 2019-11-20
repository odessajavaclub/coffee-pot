package org.odessajavaclub.user.application.port.in;

import lombok.NonNull;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

import java.util.Optional;

public interface GetUserUseCase {

    Optional<User> getUser(GetUserCommand getUserCommand);

    @Value
    class GetUserCommand {

        @NonNull
        private final User.UserId userId;
    }
}
