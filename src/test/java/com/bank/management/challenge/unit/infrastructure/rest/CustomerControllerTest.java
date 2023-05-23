package com.bank.management.challenge.unit.infrastructure.rest;

import com.bank.management.challenge.domain.models.input.CustomerInputDto;
import com.bank.management.challenge.domain.models.output.CustomerDto;
import com.bank.management.challenge.domain.ports.services.ICustomerService;
import com.bank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import com.bank.management.challenge.infrastructure.rest.CustomerController;
import com.bank.management.challenge.infrastructure.rest.input.FormatInput;
import java.util.Collections;
import java.util.Objects;
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
import org.springframework.http.HttpStatus;

/**
 * Unit tests for CustomerController.
 *
 * @author jorge-arevalo
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class CustomerControllerTest {

  @InjectMocks
  CustomerController customerController;

  @Mock
  ICustomerService customerService;

  private CustomerInputDto customerInput;

  private CustomerDto customerDto;

  private String customerId;

  @BeforeEach
  void initData() {
    customerId = UUID.randomUUID().toString();

    customerInput = CustomerInputDto.builder()
        .name("Customer Name")
        .gender("male")
        .age(25)
        .identification("1313131313")
        .address("Customer Address")
        .phoneNumber("3131313131")
        .password("Customer Password")
        .status(Boolean.TRUE)
        .build();

    customerDto = CustomerDto.builder()
        .id(customerId)
        .name(customerInput.getName())
        .gender(customerInput.getGender())
        .age(customerInput.getAge())
        .identification(customerInput.getIdentification())
        .address(customerInput.getAddress())
        .phoneNumber(customerInput.getPhoneNumber())
        .status(customerInput.getStatus())
        .build();

    log.info("CustomerControllerTest.initData - customerId: {}", customerId);
    log.info("CustomerControllerTest.initData - customerInput: {}", customerInput);
    log.info("CustomerControllerTest.initData - customerDto: {}", customerDto);
  }

  @Test
  void getAllCustomersTest() {
    Mockito.when(customerService.findAll()).thenReturn(Collections.singletonList(customerDto));
    var response = customerController.getAllCustomers();
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertEquals(1, Objects.requireNonNull(response.getBody()).getData().size());
    Assertions.assertEquals(customerId,
        Objects.requireNonNull(response.getBody()).getData().get(0).getId());
  }

  @Test
  void getCustomerByIdTest() {
    Mockito.when(customerService.findById(customerId)).thenReturn(customerDto);
    var response = customerController.getCustomerById(customerId);
    log.info("Test Response: {}", Objects.requireNonNull(response.getBody()).getData().toString());
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertEquals(customerId,
        Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void registerCustomerTest() {
    FormatInput<CustomerInputDto> input = new FormatInput<>();
    input.setData(customerInput);
    Mockito.when(customerService.save(customerInput)).thenReturn(customerDto);
    var response = customerController.registerCustomer(input);
    Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
    Assertions.assertEquals(customerId,
        Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void updateCustomerTest() {
    FormatInput<CustomerInputDto> input = new FormatInput<>();
    input.setData(customerInput);
    Mockito.when(customerService.update(customerId, customerInput)).thenReturn(customerDto);
    var response = customerController.updateCustomer(customerId, input);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertEquals(customerId,
        Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void deleteCustomerTest() {
    Mockito.doNothing().when(customerService).delete(customerId);
    var response = customerController.deleteCustomer(customerId);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertNull(Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void illegalArgumentExceptionTest() {
    customerId = "123";
    IllegalArgumentException iae = Assertions.assertThrows(IllegalArgumentException.class,
        () -> customerController.getCustomerById(customerId));
    Assertions.assertEquals(GeneralExceptionMessages.INVALID_CUSTOMER_ID, iae.getMessage());
  }

}
