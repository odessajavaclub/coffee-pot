package org.odessajavaclub.user.application.service;

class PasswordIsBlankException extends RuntimeException {

    PasswordIsBlankException() {
        super("Password must not be blank");
    }
}
