package org.odessajavaclub.user.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.user.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface CreateUserUseCase {

    User createActivatedUser(CreateUserCommand command);

    User createDeactivatedUser(CreateUserCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class CreateUserCommand extends SelfValidating<CreateUserCommand> {

        @NotNull
        @NotBlank
        private final String firstName;

        @NotNull
        @NotBlank
        private final String lastName;

        public CreateUserCommand(String firstName,
                                 String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.validateSelf();
        }
    }
}
