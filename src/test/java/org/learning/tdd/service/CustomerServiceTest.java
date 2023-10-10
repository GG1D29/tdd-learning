package org.learning.tdd.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learning.tdd.dto.CustomerDto;
import org.learning.tdd.exception.DuplicateUserException;
import org.learning.tdd.exception.NotFoundException;
import org.learning.tdd.model.Customer;
import org.learning.tdd.repository.CustomerRepository;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @Test
    void getAllCustomers() {
        Mockito.doReturn(getMockCustomers(2)).when(customerRepository).findAll();
        List<Customer> customers = customerService.getAllCustomers();
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    void getCustomer() {
        Mockito.doReturn(getMockCustomer()).when(customerRepository).findByEmailAddress("email");

        Customer customer = customerService.getCustomer("email");
        assertThat(customer.getFirstName()).isEqualTo("depan");
        assertThat(customer.getLastName()).isEqualTo("belakang");
    }

    @Test
    void getCustomer_NotFound() {
        Mockito.doReturn(Optional.empty()).when(customerRepository).findByEmailAddress(anyString());

        Exception e = assertThrows(NotFoundException.class, () -> customerService.getCustomer("hehe@hihi.com"));
        assertThat(e.getMessage()).isEqualTo("no user found with email hehe@hihi.com");
    }

    private List<Customer> getMockCustomers(int size) {
        List<Customer> customers = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            customers.add(new Customer());
        }

        return customers;
    }

    private Optional<Customer> getMockCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("depan");
        customer.setLastName("belakang");

        return Optional.of(customer);
    }

    @Test
    void createNewCustomer() {
        CustomerDto dto = getCreateCustomerDto();
        customerService.addCustomer(dto);

        Mockito.verify(customerRepository).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getCustomerId()).isNotNull();
        assertThat(capturedCustomer.getFirstName()).isEqualTo("stanley");
        assertThat(capturedCustomer.getLastName()).isEqualTo("xie");
        assertThat(capturedCustomer.getEmailAddress()).isEqualTo("me@myemail.com");
        assertThat(capturedCustomer.getPhoneNumber()).isEqualTo("phone");
        assertThat(capturedCustomer.getAddress()).isEqualTo("address");

    }

    @Test
    void createNewCustomer_Duplicate() {
        Mockito.doReturn(getMockCustomer()).when(customerRepository).findByEmailAddress(anyString());

        CustomerDto dto = getCreateCustomerDto();
        Exception e = assertThrows(DuplicateUserException.class, () -> customerService.addCustomer(dto));
        assertThat(e.getMessage()).isEqualTo("user is already exist with email me@myemail.com");
    }

    private CustomerDto getCreateCustomerDto() {
        return new CustomerDto("stanley", "xie", "me@myemail.com", "phone", "address");
    }
}