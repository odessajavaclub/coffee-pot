package org.odessajavaclub.user.application.port.out;

import org.odessajavaclub.user.domain.User;

public interface LoadUserPort {

    User loadUser(User.UserId userId);
}
