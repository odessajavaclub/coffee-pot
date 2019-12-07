package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class GetUsersService implements GetUsersQuery {

    private final LoadUsersPort loadUsersPort;

    @Override
    public List<User> getAllUsers() {
        return loadUsersPort.loadAllUsers();
    }

    @Override
    public List<User> getActiveUsers() {
        return loadUsersPort.loadActiveUsers();
    }

    @Override
    public List<User> getInactiveUsers() {
        return loadUsersPort.loadInactiveUsers();
    }

    @Override
    public Optional<User> getUserById(User.UserId userId) {
        return loadUsersPort.loadUser(userId);
    }
}
