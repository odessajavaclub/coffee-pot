package org.odessajavaclub.user.application.port.in;

import lombok.NonNull;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

public interface CreateUserUseCase {

    User.UserId createActivatedUser(CreateUserCommand command);

    User.UserId createDeactivatedUser(CreateUserCommand command);

    @Value
    class CreateUserCommand {

        @NonNull
        private final String firstName;

        @NonNull
        private final String lastName;
    }
}
