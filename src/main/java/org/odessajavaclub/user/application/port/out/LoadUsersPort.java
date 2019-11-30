package org.odessajavaclub.user.application.port.out;

import org.odessajavaclub.user.domain.User;

import java.util.List;

public interface LoadUsersPort {

    List<User> loadUsers();

    User loadUser(User.UserId userId);
}
