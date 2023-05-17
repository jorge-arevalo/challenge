package com.bank.management.challenge.domain.ports.repositories;

import com.bank.management.challenge.infrastructure.entities.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for the customer entity.
 *
 * @author jorge-arevalo
 */
public interface ICustomerRepository {

  Customer save(Customer customer);

  Optional<Customer> findById(UUID id);

  Optional<Customer> findByName(String name);

  List<Customer> findAll();

  void delete(Customer customer);

}
