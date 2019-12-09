package org.odessajavaclub.user.application.port.out;

import org.odessajavaclub.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface LoadUsersPort {

    List<User> loadAllUsers();

    List<User> loadAllUsers(int page, int size);

    List<User> loadAllUsersByActive(boolean active);

    List<User> loadAllUsersByActive(boolean active, int page, int size);

    Optional<User> loadUser(User.UserId userId);
}
