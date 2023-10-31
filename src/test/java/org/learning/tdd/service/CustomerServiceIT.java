package org.learning.tdd.service;

import org.junit.jupiter.api.Test;
import org.learning.tdd.dto.CustomerDto;
import org.learning.tdd.exception.DuplicateUserException;
import org.learning.tdd.exception.UserNotFoundException;
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
import static org.learning.tdd.service.CustomerServiceTest.getUpdateCustomerDto;
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
        Customer customer = customerService.getCustomer("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
        assertThat(customer.getFirstName()).isEqualTo("Cally");
        assertThat(customer.getLastName()).isEqualTo("Reynolds");
    }

    @Test
    void getCustomer_NotFound() {
        Exception e = assertThrows(UserNotFoundException.class, () -> customerService.getCustomer("38124691-9643-4f10-90a0-d980bca0b000"));
        assertThat(e.getMessage()).isEqualTo("user not found with id: 38124691-9643-4f10-90a0-d980bca0b000");
    }

    @Test
    void addCustomer() {
        CustomerDto dto = getCreateCustomerDto();
        UUID createdUserUUID = customerService.addCustomer(dto);
        assertThat(createdUserUUID).isNotNull();

        Customer customer = customerService.getCustomer(createdUserUUID.toString());
        assertThat(customer.getCustomerId()).isEqualTo(createdUserUUID);
        assertThat(customer.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(customer.getLastName()).isEqualTo(dto.getLastName());
    }

    @Test
    void addCustomer_Duplicate() {
        CustomerDto dto = getCreateCustomerDto();
        customerService.addCustomer(dto);

        Exception e = assertThrows(DuplicateUserException.class, () -> customerService.addCustomer(dto));
        assertThat(e.getMessage()).isEqualTo("user is already exist with email me@myemail.com");

    }

    @Test
    void updateCustomer() {
        CustomerDto dto = getUpdateCustomerDto();
        customerService.updateCustomer(dto, "9ac775c3-a1d3-4a0e-a2df-3e4ee8b3a49a");

        Customer updatedCustomer = customerService.getCustomer("9ac775c3-a1d3-4a0e-a2df-3e4ee8b3a49a");
        assertThat(updatedCustomer.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(updatedCustomer.getFirstName()).isEqualTo(dto.getFirstName());
    }

    @Test
    void updateCustomer_NotFound() {
        CustomerDto dto = getUpdateCustomerDto();
        Exception e = assertThrows(UserNotFoundException.class, () -> customerService.updateCustomer(dto, "054b145c-ddbc-4136-a2bd-7bf45ed1bbbb"));
        assertThat(e.getMessage()).isEqualTo("user not found with id: 054b145c-ddbc-4136-a2bd-7bf45ed1bbbb");
    }

    @Test
    void deleteCustomer() {
        customerService.deleteCustomer("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
        assertThrows(UserNotFoundException.class, () -> customerService.getCustomer("054b145c-ddbc-4136-a2bd-7bf45ed1bef7"));
    }
}
