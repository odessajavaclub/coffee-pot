package org.odessajavaclub.user.application.port.out;

import org.odessajavaclub.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface LoadUsersPort {

    List<User> loadAllUsers();

    List<User> loadActiveUsers();

    List<User> loadInactiveUsers();

    Optional<User> loadUser(User.UserId userId);
}
