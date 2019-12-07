package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.ActivateUserUseCase;
import org.odessajavaclub.user.application.port.in.DeactivateUserUseCase;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ActivationUserService implements ActivateUserUseCase, DeactivateUserUseCase {

    private final LoadUsersPort loadUsersPort;

    private final UpdateUserPort updateUserPort;

    @Override
    public Optional<User> activateUser(ActivateUserCommand command) {
        return loadUsersPort.loadUser(command.getUserId())
                            .map(u -> User.from(u, true))
                            .map(updateUserPort::updateUser);
    }

    @Override
    public Optional<User> deactivateUser(DeactivateUserCommand command) {
        return loadUsersPort.loadUser(command.getUserId())
                            .map(u -> User.from(u, false))
                            .map(updateUserPort::updateUser);
    }
}
