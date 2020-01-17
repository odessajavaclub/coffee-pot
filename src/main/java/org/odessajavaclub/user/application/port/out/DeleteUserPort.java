package org.odessajavaclub.user.application.port.out;

import org.odessajavaclub.user.domain.User;

public interface DeleteUserPort {

  boolean deleteUser(User.UserId userId);
}
