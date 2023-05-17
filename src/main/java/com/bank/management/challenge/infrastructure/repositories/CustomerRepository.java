package com.bank.management.challenge.infrastructure.repositories;

import com.bank.management.challenge.domain.ports.repositories.ICustomerRepository;
import com.bank.management.challenge.infrastructure.entities.Customer;
import com.bank.management.challenge.infrastructure.repositories.jpa.JpaCustomer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the repository interface for the customer entity.
 *
 * @author jorge-arevalo
 */
@Repository
@RequiredArgsConstructor
public class CustomerRepository implements ICustomerRepository {

  private final JpaCustomer jpaCustomer;

  @Override
  public Customer save(Customer customer) {
    return jpaCustomer.save(customer);
  }

  @Override
  public Optional<Customer> findById(UUID id) {
    return jpaCustomer.findById(id);
  }

  @Override
  public Optional<Customer> findByName(String name) {
    return jpaCustomer.findByName(name);
  }

  @Override
  public List<Customer> findAll() {
    return jpaCustomer.findAll();
  }

  @Override
  public void delete(Customer customer) {
    jpaCustomer.delete(customer);
  }

}
