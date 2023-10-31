package org.learning.tdd.service;

import org.learning.tdd.dto.CustomerDto;
import org.learning.tdd.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomer(String customerId);

    UUID addCustomer(CustomerDto dto);

    Customer updateCustomer(CustomerDto dto, String customerId);

    void deleteCustomer(String customerId);
}
