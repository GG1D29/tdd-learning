package org.learning.tdd.util;

import org.junit.jupiter.api.Test;
import org.learning.tdd.exception.BadRequestException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringUtilTest {

    @Test
    void create_NewUUID() {
        UUID newID = StringUtil.createNewID();
        assertThat(newID).isNotNull();
    }

    @Test
    void createUUID_fromString() {
        UUID uuid = StringUtil.fromString("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
        assertThat(uuid).isNotNull();
        assertThat(uuid.toString()).isEqualTo("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
    }

    @Test
    void createUUID_invalidString() {
        Exception e = assertThrows(BadRequestException.class, () -> StringUtil.fromString("054b145c-ddbc-4136-a2bd"));
        assertThat(e.getMessage()).isEqualTo("invalid uuid");
    }
}