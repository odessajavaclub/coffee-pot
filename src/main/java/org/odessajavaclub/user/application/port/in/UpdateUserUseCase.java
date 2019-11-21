package org.odessajavaclub.user.application.port.in;

import lombok.NonNull;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

public interface UpdateUserUseCase {

    User updateUser(UpdateUserCommand command);

    @Value
    class UpdateUserCommand {

        @NonNull
        private final User.UserId id;

        private final String newFirstName;

        private final String newLastName;
    }
}
