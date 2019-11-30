package org.odessajavaclub.user.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.odessajavaclub.shared.SelfValidating;
import org.odessajavaclub.user.domain.User;
import org.odessajavaclub.user.domain.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface CreateUserUseCase {

    User createActivatedUser(CreateUserCommand command);

    User createDeactivatedUser(CreateUserCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class CreateUserCommand extends SelfValidating<CreateUserCommand> {

        @NotBlank
        private final String firstName;

        @NotBlank
        private final String lastName;

        @Email
        private final String email;

        @NotBlank
        private final String password;

        @NotNull
        private final UserRole role;

        public CreateUserCommand(String firstName,
                                 String lastName,
                                 String email,
                                 String password,
                                 UserRole role) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.role = role;
            this.validateSelf();
        }
    }
}
