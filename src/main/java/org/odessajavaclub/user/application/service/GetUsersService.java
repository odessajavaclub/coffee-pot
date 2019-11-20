package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.GetUserUseCase;
import org.odessajavaclub.user.application.port.in.GetUsersUseCase;
import org.odessajavaclub.user.application.port.out.LoadUserPort;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.domain.User;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class GetUsersService implements GetUsersUseCase, GetUserUseCase {

    private final LoadUserPort loadUserPort;

    private final LoadUsersPort loadUsersPort;

    @Override
    public List<User> getUsers() {
        return loadUsersPort.loadUsers();
    }

    @Override
    public Optional<User> getUser(GetUserCommand getUserCommand) {
        return Optional.ofNullable(loadUserPort.loadUser(getUserCommand.getUserId()));
    }
}
