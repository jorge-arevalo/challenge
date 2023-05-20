package com.bank.management.challenge.infrastructure.repositories.jpa;

import com.bank.management.challenge.infrastructure.entities.Account;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Code description
 *
 * @author jorge-arevalo
 */
public interface JpaAccount extends JpaRepository<Account, UUID> {

  Optional<Account> findByAccountNumber(String accountNumber);

}
