package org.odessajavaclub.user.application.port.out;

import org.odessajavaclub.user.domain.User;

public interface ActivateUserPort {

    boolean activateUser(User.UserId userId);
}
