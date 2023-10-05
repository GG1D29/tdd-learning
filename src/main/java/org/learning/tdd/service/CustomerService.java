package org.learning.tdd.service;

import org.learning.tdd.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomer(String email);
}
