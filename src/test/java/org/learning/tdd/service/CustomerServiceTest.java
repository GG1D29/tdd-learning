package org.learning.tdd.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learning.tdd.exception.NotFoundException;
import org.learning.tdd.model.Customer;
import org.learning.tdd.repository.CustomerRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    void getAllCustomers() {
        Mockito.doReturn(getMockCustomers(2)).when(customerRepository).findAll();
        List<Customer> customers = customerService.getAllCustomers();
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    void getCustomer() {
        Mockito.doReturn(getMockCustomer()).when(customerRepository).findByEmailAddress("email");

        Customer customer = customerService.getCustomer("email");
        assertThat(customer.getFirstName()).isEqualTo("depan");
        assertThat(customer.getLastName()).isEqualTo("belakang");
    }

    @Test
    void getCustomer_NotFound() {
        Mockito.doReturn(Optional.empty()).when(customerRepository).findByEmailAddress(anyString());

        Exception e = assertThrows(NotFoundException.class, () -> customerService.getCustomer("hehe@hihi.com"));
        assertThat(e.getMessage()).isEqualTo("no user found with email hehe@hihi.com");
    }

    private List<Customer> getMockCustomers(int size) {
        List<Customer> customers = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            customers.add(new Customer());
        }

        return customers;
    }

    private Optional<Customer> getMockCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("depan");
        customer.setLastName("belakang");

        return Optional.of(customer);
    }
}