package org.odessajavaclub.user.application.port.out;

import org.odessajavaclub.user.domain.User;

public interface DeactivateUserPort {

    void deactivateUser(User.UserId userId);
}
