package org.odessajavaclub.user.application.service;

class FirstNameIsBlankException extends RuntimeException {

    FirstNameIsBlankException() {
        super("First name must not be blank");
    }
}
