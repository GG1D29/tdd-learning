package org.learning.tdd.util;

import org.learning.tdd.exception.BadRequestException;

import java.util.UUID;

public class StringUtil {
    public static UUID createNewID() {
        return UUID.randomUUID();
    }

    public static UUID fromString(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("invalid uuid");
        }
    }
}
