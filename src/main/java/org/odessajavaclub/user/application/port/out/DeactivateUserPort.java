package org.odessajavaclub.user.application.port.out;

import org.odessajavaclub.user.domain.User;

public interface DeactivateUserPort {

    boolean deactivateUser(User.UserId userId);
}
