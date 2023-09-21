package org.learning.tdd.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private String email;

    @Override
    public String getMessage() {
        return "no user found with email " + email;
    }
}
