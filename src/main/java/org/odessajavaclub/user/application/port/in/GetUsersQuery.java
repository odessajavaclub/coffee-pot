package org.odessajavaclub.user.application.port.in;

import org.odessajavaclub.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface GetUsersQuery {

    List<User> getAllUsers();

    List<User> getAllUsers(int page, int size);

    List<User> getAllUsersByActive(boolean active);

    List<User> getAllUsersByActive(boolean active, int page, int size);

    Optional<User> getUserById(User.UserId userId);
}
