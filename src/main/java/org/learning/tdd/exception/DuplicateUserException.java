package org.learning.tdd.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DuplicateUserException extends RuntimeException {
    private String email;

    @Override
    public String getMessage() {
        return "user is already exist with email " + email;
    }
}
