package org.learning.tdd.service;

import org.junit.jupiter.api.Test;
import org.learning.tdd.dto.CustomerDto;
import org.learning.tdd.exception.DuplicateUserException;
import org.learning.tdd.exception.NotFoundException;
import org.learning.tdd.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.learning.tdd.service.CustomerServiceTest.getCreateCustomerDto;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Transactional
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

    @Test
    void addCustomer() {
        CustomerDto dto = getCreateCustomerDto();
        UUID createdUserUUID = customerService.addCustomer(dto);
        assertThat(createdUserUUID).isNotNull();

        Customer customer = customerService.getCustomer(dto.getEmailAddress());
        assertThat(customer.getCustomerId()).isEqualTo(createdUserUUID);
        assertThat(customer.getFirstName()).isEqualTo(dto.getFirstName());
    }

    @Test
    void addCustomer_Duplicate() {
        CustomerDto dto = getCreateCustomerDto();
        customerService.addCustomer(dto);

        Exception e = assertThrows(DuplicateUserException.class, () -> customerService.addCustomer(dto));
        assertThat(e.getMessage()).isEqualTo("user is already exist with email me@myemail.com");

    }
}
