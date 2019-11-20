package org.odessajavaclub.user.application.port.in;

import org.odessajavaclub.user.domain.User;

import java.util.List;

public interface GetUsersUseCase {

    List<User> getUsers();
}
