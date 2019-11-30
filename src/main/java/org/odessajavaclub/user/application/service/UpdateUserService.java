package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class UpdateUserService implements UpdateUserUseCase {

    private final LoadUsersPort loadUserPort;

    private final UpdateUserPort updateUserPort;

    @Override
    public Optional<User> updateUser(UpdateUserCommand command) {
        User.UserId userId = Objects.requireNonNull(command.getId(), "User id must not be null");

        User existingUser = loadUserPort.loadUser(userId);
        if (existingUser == null) {
            //TODO: return from port Optional maybe?
            return Optional.empty();
        }

        User updatedUser = User.from(existingUser, command.getNewFirstName(), command.getNewLastName());

        return Optional.ofNullable(updateUserPort.updateUser(updatedUser));
    }
}
