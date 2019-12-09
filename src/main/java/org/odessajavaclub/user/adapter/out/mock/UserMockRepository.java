package org.odessajavaclub.user.adapter.out.mock;

import org.odessajavaclub.user.application.port.out.CreateUserPort;
import org.odessajavaclub.user.application.port.out.DeleteUserPort;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

//@Component
public class UserMockRepository implements CreateUserPort, LoadUsersPort, DeleteUserPort, UpdateUserPort {

    private static AtomicLong id = new AtomicLong(1L);

    private Map<Long, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        users.put(id.get(), user);
        return User.from(user, new User.UserId(id.getAndIncrement()));
    }

    @Override
    public List<User> loadAllUsers() {
        return users.entrySet()
                    .stream()
                    .map(e -> User.from(e.getValue(), new User.UserId(e.getKey())))
                    .collect(Collectors.toList());
    }

    @Override
    public List<User> loadAllUsers(int page, int size) {
        return loadAllUsers();
    }

    @Override
    public List<User> loadAllUsersByActive(boolean active) {
        return users.entrySet()
                    .stream()
                    .filter(e -> e.getValue().isActive() == active)
                    .map(e -> User.from(e.getValue(), new User.UserId(e.getKey())))
                    .collect(Collectors.toList());
    }

    @Override
    public List<User> loadAllUsersByActive(boolean active, int page, int size) {
        return loadAllUsersByActive(active);
    }

    @Override
    public Optional<User> loadUser(User.UserId userId) {
        return Optional.ofNullable(users.get(userId.getValue()))
                       .map(u -> User.from(u, userId));
    }

    @Override
    public boolean deleteUser(User.UserId userId) {
        return users.remove(userId.getValue()) != null;
    }

    @Override
    public User updateUser(User updatedUser) {
        return Optional.ofNullable(updatedUser.getId().get().getValue())
                       .map(id -> {
                           users.put(id, updatedUser);
                           return updatedUser;
                       })
                       .orElse(null);
    }
}
