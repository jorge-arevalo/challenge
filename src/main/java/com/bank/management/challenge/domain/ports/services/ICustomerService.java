package com.bank.management.challenge.domain.ports.services;

import com.bank.management.challenge.domain.models.input.CustomerInputDto;
import com.bank.management.challenge.domain.models.output.CustomerDto;
import java.util.List;

/**
 * Service interface for the customer entity.
 *
 * @author jorge-arevalo
 */
public interface ICustomerService {

  CustomerDto save(CustomerInputDto customerInput);

  CustomerDto findById(String id);

  List<CustomerDto> findAll();

  CustomerDto update(String id, CustomerInputDto customerInput);

  void delete(String id);

}
