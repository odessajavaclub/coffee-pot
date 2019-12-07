package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.out.CreateUserPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CreateUserService implements CreateUserUseCase {

    private final CreateUserPort createUserPort;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User createActiveUser(CreateUserCommand command) {
        return createUser(command, true);
    }

    @Override
    public User createInactiveUser(CreateUserCommand command) {
        return createUser(command, false);
    }

    private User createUser(CreateUserCommand command, boolean active) {
        checkFirstNameIsNotBlank(command);
        checkLastNameIsNotBlank(command);
        checkEmailIsNotBlank(command);
        checkPasswordIsNotBlank(command);
        checkRoleIsNotNull(command);

        String encodedPassword = passwordEncoder.encode(command.getPassword());

        User user = User.withoutId(command.getFirstName(),
                                   command.getLastName(),
                                   command.getEmail(),
                                   encodedPassword,
                                   command.getRole(),
                                   active);

        return createUserPort.createUser(user);
    }

    private static void checkFirstNameIsNotBlank(CreateUserCommand command) {
        if (command.getFirstName().isBlank()) {
            throw new FirstNameIsBlankException();
        }
    }

    private static void checkLastNameIsNotBlank(CreateUserCommand command) {
        if (command.getLastName().isBlank()) {
            throw new LastNameIsBlankException();
        }
    }

    private static void checkEmailIsNotBlank(CreateUserCommand command) {
        if (command.getEmail().isBlank()) {
            throw new EmailIsBlankException();
        }
    }

    private static void checkPasswordIsNotBlank(CreateUserCommand command) {
        if (command.getPassword().isBlank()) {
            throw new PasswordIsBlankException();
        }
    }

    private static void checkRoleIsNotNull(CreateUserCommand command) {
        if (command.getRole() == null) {
            throw new UserRoleIsNullException();
        }
    }
}
