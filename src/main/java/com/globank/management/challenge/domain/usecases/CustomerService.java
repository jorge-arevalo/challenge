package com.globank.management.challenge.domain.usecases;

import com.globank.management.challenge.domain.models.input.CustomerInputDto;
import com.globank.management.challenge.domain.models.output.CustomerDto;
import com.globank.management.challenge.domain.ports.repositories.ICustomerRepository;
import com.globank.management.challenge.domain.ports.services.ICustomerService;
import com.globank.management.challenge.infrastructure.config.exception.CustomerNotFoundException;
import com.globank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import com.globank.management.challenge.infrastructure.entities.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing customers.
 *
 * @author jorge-arevalo
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerService implements ICustomerService {

  private final ICustomerRepository customerRepository;

  @Override
  public CustomerDto save(CustomerInputDto customerInput) {
    var customer = Customer.builder()
        .name(customerInput.getName())
        .gender(customerInput.getGender())
        .age(customerInput.getAge())
        .identification(customerInput.getIdentification())
        .address(customerInput.getAddress())
        .phoneNumber(customerInput.getPhoneNumber())
        .password(customerInput.getPassword())
        .status(Boolean.TRUE)
        .build();
    return mapToCustomerDto(customerRepository.save(customer));
  }

  @Override
  @Transactional(readOnly = true)
  public CustomerDto findById(String id) {
    var customer = customerRepository.findById(UUID.fromString(id)).orElseThrow(
        () -> new CustomerNotFoundException(GeneralExceptionMessages.CUSTOMER_NOT_FOUND));
    return mapToCustomerDto(customer);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CustomerDto> findAll() {
    var customerList = customerRepository.findAll();
    if (customerList.isEmpty()) {
      throw new CustomerNotFoundException(GeneralExceptionMessages.CUSTOMERS_NOT_FOUND);
    }
    List<CustomerDto> customerDtoList = new ArrayList<>();
    customerList.forEach(customer -> customerDtoList.add(mapToCustomerDto(customer)));
    return customerDtoList;
  }

  @Override
  public CustomerDto update(String id, CustomerInputDto customerInput) {
    var customer = customerRepository.findById(UUID.fromString(id)).orElseThrow(
        () -> new CustomerNotFoundException(GeneralExceptionMessages.CUSTOMER_NOT_FOUND));
    customer.setName(customerInput.getName());
    customer.setGender(customerInput.getGender());
    customer.setAge(customerInput.getAge());
    customer.setIdentification(customerInput.getIdentification());
    customer.setAddress(customerInput.getAddress());
    customer.setPhoneNumber(customerInput.getPhoneNumber());
    customer.setPassword(customerInput.getPassword());
    customer.setStatus(customerInput.getStatus());
    return mapToCustomerDto(customerRepository.save(customer));
  }

  @Override
  public void delete(String id) {
    var customer = customerRepository.findById(UUID.fromString(id)).orElseThrow(
        () -> new CustomerNotFoundException(GeneralExceptionMessages.CUSTOMER_NOT_FOUND));
    customerRepository.delete(customer);
  }

  private CustomerDto mapToCustomerDto(Customer customer) {
    return CustomerDto.builder()
        .id(customer.getId())
        .name(customer.getName())
        .gender(customer.getGender())
        .age(customer.getAge())
        .identification(customer.getIdentification())
        .address(customer.getAddress())
        .phoneNumber(customer.getPhoneNumber())
        .status(customer.getStatus())
        .build();
  }

}
