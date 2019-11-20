package org.odessajavaclub.user.application.service;

class LastNameIsBlankException extends RuntimeException {

    LastNameIsBlankException() {
        super("Last name must not be blank");
    }
}
