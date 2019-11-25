package org.odessajavaclub.user.application.port.in;

import org.odessajavaclub.user.domain.User;

import java.util.List;

public interface GetUsersQuery {

    List<User> getUsers();
}
