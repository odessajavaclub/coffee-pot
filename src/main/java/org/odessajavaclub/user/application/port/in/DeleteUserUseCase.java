package org.odessajavaclub.user.application.port.in;

import lombok.NonNull;
import lombok.Value;
import org.odessajavaclub.user.domain.User;

public interface DeleteUserUseCase {

    boolean deleteUser(DeleteUserCommand command);

    @Value
    class DeleteUserCommand {

        @NonNull
        private final User.UserId userId;
    }
}
