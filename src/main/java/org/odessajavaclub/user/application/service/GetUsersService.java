package org.odessajavaclub.user.application.service;

import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.application.port.in.GetUsersQuery;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
class GetUsersService implements GetUsersQuery {

    private final LoadUsersPort loadUsersPort;

    @Override
    public List<User> getAllUsers() {
        return loadUsersPort.loadAllUsers();
    }

    @Override
    public List<User> getAllUsers(int page, int size) {
        return loadUsersPort.loadAllUsers(page, size);
    }

    @Override
    public List<User> getAllUsersByActive(boolean active) {
        return loadUsersPort.loadAllUsersByActive(active);
    }

    @Override
    public List<User> getAllUsersByActive(boolean active, int page, int size) {
        return loadUsersPort.loadAllUsersByActive(active, page, size);
    }

    @Override
    public Optional<User> getUserById(User.UserId userId) {
        return loadUsersPort.loadUser(userId);
    }
}
