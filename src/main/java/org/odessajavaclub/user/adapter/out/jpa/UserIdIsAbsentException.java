package org.odessajavaclub.user.adapter.out.jpa;

import org.odessajavaclub.user.domain.User;

class UserIdIsAbsentException extends RuntimeException {

    UserIdIsAbsentException(User user) {
        super("User id must be set for user: " + user);
    }
}
