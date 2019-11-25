package org.odessajavaclub.user.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.user.domain.User;

import javax.validation.constraints.NotNull;

public interface DeleteUserUseCase {

    boolean deleteUser(DeleteUserCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class DeleteUserCommand extends SelfValidating<DeleteUserCommand> {

        @NotNull
        private final User.UserId userId;

        public DeleteUserCommand(User.UserId userId) {
            this.userId = userId;
            this.validateSelf();
        }
    }
}
