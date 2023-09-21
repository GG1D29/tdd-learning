package org.learning.tdd.repository;

import org.learning.tdd.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    List<Customer> findAll();

    Optional<Customer> findByEmailAddress(String email);
}
