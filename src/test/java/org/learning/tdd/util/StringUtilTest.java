package org.learning.tdd.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilTest {

    @Test
    void createNewID() {
        UUID newID = StringUtil.createNewID();
        assertThat(newID).isNotNull();
    }

    @Test
    void fromString() {
        UUID uuid = StringUtil.fromString("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
        assertThat(uuid).isNotNull();
        assertThat(uuid.toString()).isEqualTo("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
    }
}