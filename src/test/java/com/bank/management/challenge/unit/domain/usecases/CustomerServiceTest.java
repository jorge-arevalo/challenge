package com.bank.management.challenge.unit.domain.usecases;

import com.bank.management.challenge.domain.models.input.CustomerInputDto;
import com.bank.management.challenge.domain.ports.repositories.ICustomerRepository;
import com.bank.management.challenge.domain.usecases.CustomerService;
import com.bank.management.challenge.infrastructure.config.exception.CustomerNotFoundException;
import com.bank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import com.bank.management.challenge.infrastructure.entities.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test for CustomerService.
 *
 * @author jorge-arevalo
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class CustomerServiceTest {

  @InjectMocks
  CustomerService customerService;

  @Mock
  ICustomerRepository customerRepository;

  @Captor
  ArgumentCaptor<Customer> customerCaptor;

  private CustomerInputDto customerInput;

  private Customer customer;

  private UUID customerId;

  @BeforeEach
  void initData() {
    customerId = UUID.randomUUID();

    customerInput = CustomerInputDto.builder()
        .name("Customer Name")
        .gender("male")
        .age(30)
        .identification("123456789")
        .address("Customer Address")
        .phoneNumber("123456789")
        .password("Customer Password")
        .status(Boolean.TRUE)
        .build();

    customer = Customer.builder()
        .id(customerId)
        .name(customerInput.getName())
        .gender(customerInput.getGender())
        .age(customerInput.getAge())
        .identification(customerInput.getIdentification())
        .address(customerInput.getAddress())
        .phoneNumber(customerInput.getPhoneNumber())
        .password(customerInput.getPassword())
        .status(Boolean.TRUE)
        .build();

    log.info("CustomerServiceTest.initData() - customerInput: {}", customerInput);
    log.info("CustomerServiceTest.initData() - customer: {}", customer);
  }

  @Test
  void saveTest() {
    Mockito.lenient().when(customerRepository.save(customerCaptor.capture())).thenReturn(customer);
    var result = customerService.save(customerInput);
    Assertions.assertEquals(customerId.toString(), result.getId());
  }

  @Test
  void findByIdTest() {
    Mockito.lenient().when(customerRepository.findById(customerId))
        .thenReturn(Optional.of(customer));
    var result = customerService.findById(customerId.toString());
    Assertions.assertEquals(customerId.toString(), result.getId());
  }

  @Test
  void findByIdNotFoundTest() {
    var customerIdStr = customerId.toString();
    Mockito.lenient().when(customerRepository.findById(customerId))
        .thenReturn(Optional.empty());
    CustomerNotFoundException cnf = Assertions.assertThrows(CustomerNotFoundException.class,
        () -> customerService.findById(customerIdStr));
    Assertions.assertEquals(GeneralExceptionMessages.CUSTOMER_NOT_FOUND, cnf.getMessage());
  }

  @Test
  void findAllTest() {
    Mockito.lenient().when(customerRepository.findAll()).thenReturn(List.of(customer));
    var result = customerService.findAll();
    Assertions.assertEquals(customerId.toString(), result.get(0).getId());
  }

  @Test
  void findAllEmptyTest() {
    Mockito.lenient().when(customerRepository.findAll()).thenReturn(List.of());
    CustomerNotFoundException cnf = Assertions.assertThrows(CustomerNotFoundException.class,
        () -> customerService.findAll());
    Assertions.assertEquals(GeneralExceptionMessages.CUSTOMERS_NOT_FOUND, cnf.getMessage());
  }

  @Test
  void updateTest() {
    Mockito.lenient().when(customerRepository.findById(customerId))
        .thenReturn(Optional.of(customer));
    Mockito.lenient().when(customerRepository.save(customerCaptor.capture())).thenReturn(customer);
    var result = customerService.update(customerId.toString(), customerInput);
    Assertions.assertEquals(customerId.toString(), result.getId());
  }

  @Test
  void updateNotFoundTest() {
    var customerIdStr = customerId.toString();
    Mockito.lenient().when(customerRepository.findById(customerId))
        .thenReturn(Optional.empty());
    CustomerNotFoundException cnf = Assertions.assertThrows(CustomerNotFoundException.class,
        () -> customerService.update(customerIdStr, customerInput));
    Assertions.assertEquals(GeneralExceptionMessages.CUSTOMER_NOT_FOUND, cnf.getMessage());
  }

  @Test
  void deleteTest() {
    Mockito.lenient().when(customerRepository.findById(customerId))
        .thenReturn(Optional.of(customer));
    Mockito.lenient().doNothing().when(customerRepository).delete(customer);
    customerService.delete(customerId.toString());
    Mockito.verify(customerRepository, Mockito.times(1)).delete(customer);
  }

  @Test
  void deleteNotFoundTest() {
    var customerIdStr = customerId.toString();
    Mockito.lenient().when(customerRepository.findById(customerId))
        .thenReturn(Optional.empty());
    CustomerNotFoundException cnf = Assertions.assertThrows(CustomerNotFoundException.class,
        () -> customerService.delete(customerIdStr));
    Assertions.assertEquals(GeneralExceptionMessages.CUSTOMER_NOT_FOUND, cnf.getMessage());
  }

}
