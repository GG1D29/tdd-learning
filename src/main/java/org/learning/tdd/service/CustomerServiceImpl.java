package org.learning.tdd.service;

import lombok.AllArgsConstructor;
import org.learning.tdd.dto.CustomerDto;
import org.learning.tdd.exception.DuplicateUserException;
import org.learning.tdd.exception.NotFoundException;
import org.learning.tdd.model.Customer;
import org.learning.tdd.repository.CustomerRepository;
import org.learning.tdd.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Customer> customerOptional = customerRepository.findByEmailAddress(dto.getEmailAddress());
        if (customerOptional.isPresent()) {
            throw new DuplicateUserException(dto.getEmailAddress());
        }

        Customer customer = new Customer(StringUtil.createNewID(), dto.getFirstName(), dto.getLastName(), dto.getEmailAddress(), dto.getPhoneNumber(), dto.getAddress());
        customerRepository.save(customer);
    }


}
