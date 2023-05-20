package com.bank.management.challenge.unit.infrastructure.repositories;

import com.bank.management.challenge.infrastructure.entities.Customer;
import com.bank.management.challenge.infrastructure.repositories.CustomerRepository;
import com.bank.management.challenge.infrastructure.repositories.jpa.JpaCustomer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test for CustomerRepository.
 *
 * @author jorge-arevalo
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class CustomerRepositoryTest {

  @InjectMocks
  CustomerRepository customerRepository;

  @Mock
  JpaCustomer jpaCustomer;

  private Customer customer;

  private UUID customerId;

  private String customerName;

  @BeforeEach
  public void initData() {
    customerId = UUID.randomUUID();
    customerName = "Customer Name";
    customer = Customer.builder()
        .id(customerId)
        .name(customerName)
        .gender("male")
        .age(30)
        .identification("123456789")
        .address("Customer Address")
        .phoneNumber("123456789")
        .password("Customer Password")
        .status(Boolean.TRUE)
        .build();

    log.info("CustomerRepositoryTest.initData() - customer: {}", customer);
  }

  @Test
  void saveTest() {
    Mockito.when(jpaCustomer.save(customer)).thenReturn(customer);
    var result = customerRepository.save(customer);
    Assertions.assertEquals(customer, result);
  }

  @Test
  void findByIdTest() {
    Mockito.when(jpaCustomer.findById(customerId)).thenReturn(Optional.of(customer));
    var result = customerRepository.findById(customerId);
    var customerRes = result.orElseGet(Customer::new);
    Assertions.assertEquals(customer, customerRes);
  }

  @Test
  void findByNameTest() {
    Mockito.when(jpaCustomer.findByName(customerName)).thenReturn(Optional.of(customer));
    var result = customerRepository.findByName(customerName);
    var customerRes = result.orElseGet(Customer::new);
    Assertions.assertEquals(customer, customerRes);
  }

  @Test
  void findAllTest() {
    Mockito.when(jpaCustomer.findAll()).thenReturn(List.of(customer));
    var result = customerRepository.findAll();
    Assertions.assertEquals(List.of(customer), result);
  }

  @Test
  void deleteTest() {
    Mockito.doNothing().when(jpaCustomer).delete(customer);
    customerRepository.delete(customer);
    Mockito.verify(jpaCustomer, Mockito.times(1)).delete(customer);
  }

}
