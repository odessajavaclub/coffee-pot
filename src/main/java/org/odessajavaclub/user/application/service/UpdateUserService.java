package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.UpdateUserUseCase;
import org.odessajavaclub.user.application.port.out.LoadUserPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;

import java.util.Objects;

@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final LoadUserPort loadUserPort;

    private final UpdateUserPort updateUserPort;

    @Override
    public User updateUser(UpdateUserCommand command) {
        User.UserId userId = Objects.requireNonNull(command.getId(), "User id must not be null");

        User existingUser = loadUserPort.loadUser(userId);

        User updatedUser = User.from(existingUser, command.getNewFirstName(), command.getNewLastName());

        return updateUserPort.updateUser(updatedUser);
    }
}
