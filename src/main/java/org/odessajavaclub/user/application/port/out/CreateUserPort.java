package org.odessajavaclub.user.application.port.out;

import org.odessajavaclub.user.domain.User;

public interface CreateUserPort {

  User createUser(User user);
}
