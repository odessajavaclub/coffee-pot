package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.event.UserUpdatedEvent;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class UpdateUserService implements UpdateUserUseCase {

    private final LoadUsersPort loadUserPort;

    private final UpdateUserPort updateUserPort;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Optional<User> updateUser(UpdateUserCommand command) {
        User.UserId userId = Objects.requireNonNull(command.getId(), "User id must not be null");

        Optional<User> existingUser = loadUserPort.loadUser(userId);

        Optional<User> updatedUser = existingUser.map(user -> User.from(user,
                                                                        command.getNewFirstName(),
                                                                        command.getNewLastName(),
                                                                        command.getNewEmail()))
                                                 .map(updateUserPort::updateUser);

        if (existingUser.isPresent() && updatedUser.isPresent()) {
            applicationEventPublisher.publishEvent(new UserUpdatedEvent(existingUser.get(), updatedUser.get()));
        }
        return updatedUser;
    }
}
