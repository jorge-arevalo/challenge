package com.globank.management.challenge.infrastructure.repositories.jpa;

import com.globank.management.challenge.infrastructure.entities.Customer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for Customer entity.
 *
 * @author jorge-arevalo
 */
public interface JpaCustomer extends JpaRepository<Customer, UUID> {

  Optional<Customer> findByName(String name);

}
