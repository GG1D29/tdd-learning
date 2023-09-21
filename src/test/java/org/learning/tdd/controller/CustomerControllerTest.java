package org.learning.tdd.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.ANY)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc; // it creates pseudo dispatcher servlet

    @Test
    void getCustomers() throws Exception {
        mockMvc.perform(get("/customers")).andExpect(status().isOk())
                .andExpect(content().string(containsString("054b145c-ddbc-4136-a2bd-7bf45ed1bef7")))
                .andExpect(content().string(containsString("38124691-9643-4f10-90a0-d980bca0b27d")));
    }

    @Test
    void getCustomer() throws Exception {
        mockMvc.perform(get("/customers/nibh@ultricesposuere.edu")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Sydney")))
                .andExpect(content().string(containsString("Bartlett")));
    }

    @Test
    void getCustomer_NotFound() throws Exception {
        mockMvc.perform(get("/customers/something-else")).andExpect(status().isNotFound());
    }
}