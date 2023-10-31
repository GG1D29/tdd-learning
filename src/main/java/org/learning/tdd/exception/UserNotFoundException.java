package org.learning.tdd.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private String id;

    @Override
    public String getMessage() {
        return "user not found with id: " + id;
    }
}
