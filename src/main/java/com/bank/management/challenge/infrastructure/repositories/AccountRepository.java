package com.bank.management.challenge.infrastructure.repositories;

import com.bank.management.challenge.domain.ports.repositories.IAccountRepository;
import com.bank.management.challenge.infrastructure.entities.Account;
import com.bank.management.challenge.infrastructure.repositories.jpa.JpaAccount;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the repository interface for the Account entity.
 *
 * @author jorge-arevalo
 */
@Repository
@RequiredArgsConstructor
public class AccountRepository implements IAccountRepository {

  private final JpaAccount jpaAccount;

  @Override
  public Account save(Account account) {
    return jpaAccount.save(account);
  }

  @Override
  public Optional<Account> findById(UUID id) {
    return jpaAccount.findById(id);
  }

  @Override
  public Optional<Account> findByAccountNumber(String accountNumber) {
    return jpaAccount.findByAccountNumber(accountNumber);
  }

  @Override
  public List<Account> findAll() {
    return jpaAccount.findAll();
  }

  @Override
  public void delete(Account account) {
    jpaAccount.delete(account);
  }

}
