package org.odessajavaclub.user.application.port.in;

import lombok.NonNull;
import lombok.Value;

public interface CreateUserUseCase {

    boolean createUser(CreateUserCommand command);

    @Value
    class CreateUserCommand {

        @NonNull
        private final String firstName;

        @NonNull
        private final String lastName;
    }
}
