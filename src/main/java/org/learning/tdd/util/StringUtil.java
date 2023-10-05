package org.learning.tdd.util;

import java.util.UUID;

public class StringUtil {
    public static UUID createNewID() {
        return UUID.randomUUID();
    }

    public static UUID fromString(String id) {
        return UUID.fromString(id);
    }
}
