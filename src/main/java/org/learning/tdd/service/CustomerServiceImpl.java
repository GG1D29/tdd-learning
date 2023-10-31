package org.learning.tdd.service;

import lombok.AllArgsConstructor;
import org.learning.tdd.dto.CustomerDto;
import org.learning.tdd.exception.DuplicateUserException;
import org.learning.tdd.exception.UserNotFoundException;
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
    public Customer getCustomer(String customerId) {
        UUID uuid = StringUtil.convertToUUID(customerId);
        return customerRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException(customerId));
    }

    @Override
    public UUID addCustomer(CustomerDto dto) {
        validateDuplicateUser(dto.getEmailAddress());

        Customer customer = new Customer(StringUtil.createNewID(), dto.getFirstName(), dto.getLastName(), dto.getEmailAddress(), dto.getPhoneNumber(), dto.getAddress());
        customerRepository.save(customer);

        return customer.getCustomerId();
    }


    @Override
    public Customer updateCustomer(CustomerDto dto, String customerId) {
        UUID uuid = StringUtil.convertToUUID(customerId);
        validateExistingUser(uuid);

        Customer customer = new Customer(uuid, dto.getFirstName(), dto.getLastName(), dto.getEmailAddress(),
                dto.getPhoneNumber(), dto.getAddress());
        customerRepository.save(customer);

        return customer;
    }

    @Override
    public void deleteCustomer(String customerId) {
        UUID uuid = StringUtil.convertToUUID(customerId);
        validateExistingUser(uuid);

        customerRepository.deleteById(uuid);
    }

    private void validateExistingUser(UUID customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new UserNotFoundException(customerId.toString());
        }
    }

    private void validateDuplicateUser(String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmailAddress(email);
        if (customerOptional.isPresent()) {
            throw new DuplicateUserException(email);
        }
    }
}
