package org.odessajavaclub.user.application.service;

class EmailIsBlankException extends RuntimeException {

    EmailIsBlankException() {
        super("Email must not be blank");
    }
}
