package org.odessajavaclub.user.application.port.in;

import lombok.NonNull;
import lombok.Value;
import org.odessajavaclub.user.domain.User.UserId;

public interface DeleteUserUseCase {

    boolean deleteUser(DeleteUserCommand command);

    @Value
    class DeleteUserCommand {

        @NonNull
        private final UserId userId;
    }
}
