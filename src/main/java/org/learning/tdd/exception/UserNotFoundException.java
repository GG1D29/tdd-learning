package org.learning.tdd.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "user not found";
    }
}
