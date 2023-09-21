package org.learning.tdd.service;

import org.junit.jupiter.api.Test;
import org.learning.tdd.exception.NotFoundException;
import org.learning.tdd.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class CustomerServiceIT {
    @Autowired
    CustomerService customerService;

    @Test
    void getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        assertThat(customers.size()).isEqualTo(5);
    }

    @Test
    void getCustomer() {
        Customer customer = customerService.getCustomer("penatibus.et@lectusa.com");
        assertThat(customer.getFirstName()).isEqualTo("Cally");
        assertThat(customer.getLastName()).isEqualTo("Reynolds");
    }

    @Test
    void getCustomer_NotFound() {
        Exception e = assertThrows(NotFoundException.class, () -> customerService.getCustomer("someone@lectusa.com"));
        assertThat(e.getMessage()).isEqualTo("no user found with email someone@lectusa.com");
    }
}
