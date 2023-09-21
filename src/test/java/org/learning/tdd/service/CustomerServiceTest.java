package org.learning.tdd.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learning.tdd.model.Customer;
import org.learning.tdd.repository.CustomerRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(2, customers.size());
    }

    private List<Customer> getMockCustomers(int size) {
        List<Customer> customers = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            customers.add(new Customer());
        }

        return customers;
    }


}