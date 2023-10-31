package org.learning.tdd.controller;

import lombok.AllArgsConstructor;
import org.learning.tdd.dto.CustomerDto;
import org.learning.tdd.model.Customer;
import org.learning.tdd.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable("id") String id) {
        return customerService.getCustomer(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID addCustomer(@RequestBody CustomerDto dto) {
        return customerService.addCustomer(dto);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable("id") String id, @RequestBody CustomerDto dto) {
        return customerService.updateCustomer(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteCustomer(@PathVariable("id") String id) {
        customerService.deleteCustomer(id);
    }
}
