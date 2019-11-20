package org.odessajavaclub.user.application.port.out;

import org.odessajavaclub.user.domain.User;

public interface DeleteUserPort {

    void deleteUser(User.UserId userId);
}
