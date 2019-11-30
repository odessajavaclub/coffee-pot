package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.GetUserQuery;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.application.port.out.LoadUserPort;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class GetUsersService implements GetUsersQuery, GetUserQuery {

    private final LoadUserPort loadUserPort;

    private final LoadUsersPort loadUsersPort;

    @Override
    public List<User> getUsers() {
        return loadUsersPort.loadUsers();
    }

    @Override
    public Optional<User> getUser(UserQuery userQuery) {
        return Optional.ofNullable(loadUserPort.loadUser(userQuery.getUserId()));
    }
}