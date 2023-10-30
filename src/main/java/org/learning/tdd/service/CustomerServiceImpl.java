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
import java.util.UUID;

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
        //TODO get customer use UUID instead
        return customerRepository.findByEmailAddress(email).orElseThrow(() -> new NotFoundException(email));
    }

    @Override
    public UUID addCustomer(CustomerDto dto) {
        Optional<Customer> customerOptional = customerRepository.findByEmailAddress(dto.getEmailAddress());
        if (customerOptional.isPresent()) {
            throw new DuplicateUserException(dto.getEmailAddress());
        }

        Customer customer = new Customer(StringUtil.createNewID(), dto.getFirstName(), dto.getLastName(), dto.getEmailAddress(), dto.getPhoneNumber(), dto.getAddress());
        customerRepository.save(customer);

        return customer.getCustomerId();
    }


    @Override
    public void updateCustomer(CustomerDto dto, String customerId) {
        Customer customer = new Customer(UUID.fromString(customerId), dto.getFirstName(), dto.getLastName(), dto.getEmailAddress(), dto.getPhoneNumber(), dto.getAddress());
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(String customerId) {
        //TODO handle when String customerId is not correct UUID
        customerRepository.deleteById(UUID.fromString(customerId));
    }
}
