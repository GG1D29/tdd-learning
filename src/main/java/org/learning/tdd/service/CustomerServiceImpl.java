package org.learning.tdd.service;

import lombok.AllArgsConstructor;
import org.learning.tdd.dto.CustomerDto;
import org.learning.tdd.exception.NotFoundException;
import org.learning.tdd.model.Customer;
import org.learning.tdd.repository.CustomerRepository;
import org.learning.tdd.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(String email) {
        return customerRepository.findByEmailAddress(email).orElseThrow(() -> new NotFoundException(email));
    }

    public void addCustomer(CustomerDto dto) {
        Customer customer = new Customer(StringUtil.createNewID(), dto.getFirstName(), dto.getLastName(), dto.getEmailAddress(), dto.getPhoneNumber(), dto.getAddress());
        customerRepository.save(customer);
    }
}
