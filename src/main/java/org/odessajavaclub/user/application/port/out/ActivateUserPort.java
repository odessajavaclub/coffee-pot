package org.odessajavaclub.user.application.port.out;

import org.odessajavaclub.user.domain.User;

public interface ActivateUserPort {

    void activateUser(User.UserId userId);
}
