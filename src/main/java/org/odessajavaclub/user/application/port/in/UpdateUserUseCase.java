package org.odessajavaclub.user.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.user.domain.User;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UpdateUserUseCase {

    Optional<User> updateUser(UpdateUserCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class UpdateUserCommand extends SelfValidating<UpdateUserCommand> {

        @NotNull
        private final User.UserId id;

        private final String newFirstName;

        private final String newLastName;

        private final String newEmail;

        public UpdateUserCommand(User.UserId id,
                                 String newFirstName,
                                 String newLastName,
                                 String newEmail) {
            this.id = id;
            this.newFirstName = newFirstName;
            this.newLastName = newLastName;
            this.newEmail = newEmail;
            this.validateSelf();
        }
    }
}
