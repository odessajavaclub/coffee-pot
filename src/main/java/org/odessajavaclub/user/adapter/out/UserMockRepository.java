package org.odessajavaclub.user.adapter.out;

import org.odessajavaclub.user.application.port.out.ActivateUserPort;
import org.odessajavaclub.user.application.port.out.CreateUserPort;
import org.odessajavaclub.user.application.port.out.DeactivateUserPort;
import org.odessajavaclub.user.application.port.out.DeleteUserPort;
import org.odessajavaclub.user.application.port.out.LoadUserPort;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class UserMockRepository implements CreateUserPort,
                                           LoadUsersPort,
                                           LoadUserPort,
                                           DeleteUserPort,
                                           UpdateUserPort,
                                           ActivateUserPort,
                                           DeactivateUserPort {

    private static AtomicLong id = new AtomicLong(1L);

    private Map<Long, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        users.put(id.get(), user);
        return User.from(user, new User.UserId(id.getAndIncrement()));
    }

    @Override
    public List<User> loadUsers() {
        return users.entrySet()
                    .stream()
                    .map(e -> User.from(e.getValue(), new User.UserId(e.getKey())))
                    .collect(Collectors.toList());
    }

    @Override
    public User loadUser(User.UserId userId) {
        return Optional.ofNullable(users.get(userId.getValue()))
                       .map(u -> User.from(u, userId))
                       .orElse(null);
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

    @Override
    public boolean activateUser(User.UserId userId) {
        return Optional.ofNullable(users.get(userId.getValue()))
                       .map(user -> User.from(user, false))
                       .map(user -> users.put(userId.getValue(), user))
                       .isPresent();
    }

    @Override
    public boolean deactivateUser(User.UserId userId) {
        return Optional.ofNullable(users.get(userId.getValue()))
                       .map(user -> User.from(user, true))
                       .map(user -> users.put(userId.getValue(), user))
                       .isPresent();
    }
}
