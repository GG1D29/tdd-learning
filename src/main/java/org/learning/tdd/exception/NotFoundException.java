package org.learning.tdd.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private String email;

    @Override
    public String getMessage() {
        return "no user found with email " + email;
    }
}
