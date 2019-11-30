package org.odessajavaclub.user.application.service;

class UserRoleIsNullException extends RuntimeException {

    UserRoleIsNullException() {
        super("User role must not be null");
    }
}
