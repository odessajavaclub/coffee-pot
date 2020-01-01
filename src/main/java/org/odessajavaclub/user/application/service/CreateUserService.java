package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.out.CreateUserPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
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
        Objects.requireNonNull(command, "CreateUserCommand must not be null");

        String encodedPassword = passwordEncoder.encode(command.getPassword());

        User user = User.withoutId(command.getFirstName(),
                                   command.getLastName(),
                                   command.getEmail(),
                                   encodedPassword,
                                   command.getRole(),
                                   active);

        return createUserPort.createUser(user);
    }
}
