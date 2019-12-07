package org.odessajavaclub.user.application.port.in;

import org.odessajavaclub.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface GetUsersQuery {

    List<User> getAllUsers();

    List<User> getActiveUsers();

    List<User> getInactiveUsers();

    Optional<User> getUserById(User.UserId userId);
}
