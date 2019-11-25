package org.odessajavaclub.user.application.port.in;

import lombok.NonNull;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

import java.util.Optional;

public interface GetUserQuery {

    Optional<User> getUser(UserQuery userQuery);

    @Value
    class UserQuery {

        @NonNull
        private final User.UserId userId;
    }
}
