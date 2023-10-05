package org.learning.tdd.controller;

import lombok.AllArgsConstructor;
import org.learning.tdd.model.Customer;
import org.learning.tdd.service.CustomerService;
import org.learning.tdd.service.CustomerServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{email}")
    public Customer getCustomer(@PathVariable("email") String email) {
        return customerService.getCustomer(email);
    }
}
