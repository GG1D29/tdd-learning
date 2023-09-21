package org.learning.tdd.service;

import lombok.AllArgsConstructor;
import org.learning.tdd.exception.NotFoundException;
import org.learning.tdd.model.Customer;
import org.learning.tdd.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmailAddress(email);
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        }

        throw new NotFoundException(email);
    }
}
