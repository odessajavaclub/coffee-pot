package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.event.UserCreatedEvent;
import org.odessajavaclub.user.application.port.in.CreateUserUseCase;
import org.odessajavaclub.user.application.port.out.CreateUserPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
class CreateUserService implements CreateUserUseCase {

    private final CreateUserPort createUserPort;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public User createActiveUser(CreateUserCommand command) {
        User user = createUser(command, true);
        applicationEventPublisher.publishEvent(new UserCreatedEvent(user));
        return user;
    }

    @Override
    public User createInactiveUser(CreateUserCommand command) {
        User user = createUser(command, false);
        applicationEventPublisher.publishEvent(new UserCreatedEvent(user));
        return user;
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
