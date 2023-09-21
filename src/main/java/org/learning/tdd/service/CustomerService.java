package org.learning.tdd.service;

import lombok.AllArgsConstructor;
import org.learning.tdd.model.Customer;
import org.learning.tdd.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
