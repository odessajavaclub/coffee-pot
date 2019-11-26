package org.odessajavaclub.category.application.service;

class BlankNameException extends RuntimeException {
    BlankNameException() {
        super("Category name can't be empty");
    }
}
