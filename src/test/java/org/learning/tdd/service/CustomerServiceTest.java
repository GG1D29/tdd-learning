package org.learning.tdd.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.learning.tdd.dto.CustomerDto;
import org.learning.tdd.exception.BadRequestException;
import org.learning.tdd.exception.DuplicateUserException;
import org.learning.tdd.exception.UserNotFoundException;
import org.learning.tdd.model.Customer;
import org.learning.tdd.repository.CustomerRepository;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Test
    void getAllCustomers() {
        Mockito.doReturn(getMockCustomers(2)).when(customerRepository).findAll();
        List<Customer> customers = customerService.getAllCustomers();
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    void getCustomer() {
        Customer mockCustomer = getMockCustomer1();
        Mockito.doReturn(Optional.of(mockCustomer)).when(customerRepository).findById(mockCustomer.getCustomerId());

        Customer customer = customerService.getCustomer("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
        assertThat(customer.getFirstName()).isEqualTo("depan");
        assertThat(customer.getLastName()).isEqualTo("belakang");
    }

    @Test
    void getCustomer_NotFound() {
        Mockito.doReturn(Optional.empty()).when(customerRepository).findById(any(UUID.class));

        Exception e = assertThrows(UserNotFoundException.class, () -> customerService.getCustomer("054b145c-ddbc-4136-a2bd-7bf45ed1bef7"));
        assertThat(e.getMessage()).isEqualTo("user not found with id: 054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
    }

    @Test
    void getCustomer_InvalidCustomerId() {
        Exception e = assertThrows(BadRequestException.class, () -> customerService.getCustomer("123"));
        assertThat(e.getMessage()).isEqualTo("invalid id");
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

    private Customer getMockCustomer1() {
        Customer customer = new Customer();
        UUID uuid = UUID.fromString("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
        customer.setCustomerId(uuid);
        customer.setFirstName("depan");
        customer.setLastName("belakang");

        return customer;
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

    static CustomerDto getCreateCustomerDto() {
        return new CustomerDto("stanley", "xie", "me@myemail.com", "phone", "address");
    }

    @Test
    void updateCustomer() {
        CustomerDto dto = getUpdateCustomerDto();
        customerService.updateCustomer(dto, "054b145c-ddbc-4136-a2bd-7bf45ed1bef7");

        Mockito.verify(customerRepository).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        UUID uuid = UUID.fromString("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
        assertThat(capturedCustomer.getCustomerId()).isEqualTo(uuid);
        assertThat(capturedCustomer.getFirstName()).isEqualTo("stanley1");
        assertThat(capturedCustomer.getLastName()).isEqualTo("xie1");
        assertThat(capturedCustomer.getEmailAddress()).isEqualTo("new_me@myemail.com");
        assertThat(capturedCustomer.getPhoneNumber()).isEqualTo("phone1");
        assertThat(capturedCustomer.getAddress()).isEqualTo("address1");
    }

    static CustomerDto getUpdateCustomerDto() {
        return new CustomerDto("stanley1", "xie1", "new_me@myemail.com", "phone1", "address1");
    }

    @Test
    void deleteCustomer() {
        customerService.deleteCustomer("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");

        Mockito.verify(customerRepository).deleteById(uuidArgumentCaptor.capture());
        UUID capturedCustomer = uuidArgumentCaptor.getValue();
        UUID uuid = UUID.fromString("054b145c-ddbc-4136-a2bd-7bf45ed1bef7");
        assertThat(capturedCustomer).isEqualTo(uuid);
    }
}