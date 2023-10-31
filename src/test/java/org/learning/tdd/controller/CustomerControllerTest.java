package org.learning.tdd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.learning.tdd.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        mockMvc.perform(get("/customers/9ac775c3-a1d3-4a0e-a2df-3e4ee8b3a49a")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Sydney")))
                .andExpect(content().string(containsString("Bartlett")));
    }

    @Test
    void getCustomer_InvalidID() throws Exception {
        mockMvc.perform(get("/customers/something-else")).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("cannot convert string to uuid")));
    }

    @Test
    void getCustomer_NotFound() throws Exception {
        mockMvc.perform(get("/customers/9ac775c3-a1d3-4a0e-a2df-3e4ee8b3abbb"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("user not found")));
    }

    @Test
    void addCustomer() throws Exception {
        CustomerDto customer = new CustomerDto("John", "Doe", "jdoe@test.com", "555-515-1234", "1234 Main St, Smallville KS 66083");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(customer);

        this.mockMvc.perform(post("/customers").content(jsonString).contentType("application/json")).andExpect(status().isCreated())
                .andExpect(content().string(notNullValue()));
    }

    @Test
    void addCustomer_Duplicate() throws Exception {
        CustomerDto customer = new CustomerDto("John", "Doe", "nibh@ultricesposuere.edu", "555-515-1234", "1234 Main St, Smallville KS 66083");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(customer);

        this.mockMvc.perform(post("/customers").content(jsonString).contentType("application/json"))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("user is already exist")));
    }

    @Test
    void updateCustomer() throws Exception {
        CustomerDto customer = new CustomerDto("Jack", "Bower", "quam.quis.diam@facilisisfacilisis.org", "(831) 996-1240", "2 Rockefeller Avenue, Waco, TX 76796");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(customer);
        this.mockMvc.perform(put("/customers/c04ca077-8c40-4437-b77a-41f510f3f185").content(jsonString).contentType("application/json")).andExpect(status().isOk())
                .andExpect(content().string(containsString("c04ca077-8c40-4437-b77a-41f510f3f185")))
                .andExpect(content().string(containsString("Jack")))
                .andExpect(content().string(containsString("Bower")));
    }

    @Test
    void updateCustomer_NotFound() throws Exception {
        CustomerDto customer = new CustomerDto("Jack", "Bower", "quam.quis.diam@facilisisfacilisis.org", "(831) 996-1240", "2 Rockefeller Avenue, Waco, TX 76796");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(customer);
        this.mockMvc.perform(put("/customers/c04ca077-8c40-4437-b77a-41f510f35555").content(jsonString).contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("user not found")));
    }

    @Test
    void updateCustomer_InvalidID() throws Exception {
        CustomerDto customer = new CustomerDto("Jack", "Bower", "quam.quis.diam@facilisisfacilisis.org", "(831) 996-1240", "2 Rockefeller Avenue, Waco, TX 76796");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(customer);
        this.mockMvc.perform(put("/customers/c04ca077-8c40-4437-b77a").content(jsonString).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("cannot convert string to uuid")));
    }

    @Test
    void deleteCustomer() throws Exception {
        this.mockMvc.perform(delete("/customers/3b6c3ecc-fad7-49db-a14a-f396ed866e50")).andExpect(status().isResetContent());
    }

    @Test
    void deleteCustomer_NotFound() throws Exception {
        this.mockMvc.perform(delete("/customers/3b6c3ecc-fad7-49db-a14a-f396ed866666"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("user not found")));
    }

    @Test
    void deleteCustomer_InvalidID() throws Exception {
        this.mockMvc.perform(delete("/customers/3b6c3ecc-fad7-49db-a14a"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("cannot convert string to uuid")));
    }
}