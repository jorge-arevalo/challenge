package com.bank.management.challenge.unit.infrastructure.repositories;

import com.bank.management.challenge.infrastructure.entities.Account;
import com.bank.management.challenge.infrastructure.repositories.AccountRepository;
import com.bank.management.challenge.infrastructure.repositories.jpa.JpaAccount;
import java.math.BigDecimal;
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
 * Unit test for AccountRepository.
 *
 * @author jorge-arevalo
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class AccountRepositoryTest {

  @InjectMocks
  AccountRepository accountRepository;

  @Mock
  JpaAccount jpaAccount;

  private Account account;

  private UUID accountId;

  private String accountNumber;

  @BeforeEach
  public void initData() {
    accountId = UUID.randomUUID();
    accountNumber = "123456789";
    account = Account.builder()
        .id(accountId)
        .accountNumber(accountNumber)
        .accountType("savings")
        .initialBalance(BigDecimal.TEN)
        .status(Boolean.TRUE)
        .customer(null)
        .build();

    log.info("AccountRepositoryTest.initData() - account: {}", account);
  }

  @Test
  void saveTest() {
    Mockito.when(jpaAccount.save(account)).thenReturn(account);
    var result = accountRepository.save(account);
    Assertions.assertEquals(account, result);
  }

  @Test
  void findByIdTest() {
    Mockito.when(jpaAccount.findById(accountId)).thenReturn(Optional.ofNullable(account));
    var result = accountRepository.findById(accountId);
    var accountRes = result.orElseGet(Account::new);
    Assertions.assertEquals(account, accountRes);
  }

  @Test
  void findByAccountNumberTest() {
    Mockito.when(jpaAccount.findByAccountNumber(accountNumber))
        .thenReturn(Optional.ofNullable(account));
    var result = accountRepository.findByAccountNumber(accountNumber);
    var accountRes = result.orElseGet(Account::new);
    Assertions.assertEquals(account, accountRes);
  }

  @Test
  void findAllTest() {
    Mockito.when(jpaAccount.findAll()).thenReturn(List.of(account));
    var result = accountRepository.findAll();
    Assertions.assertEquals(List.of(account), result);
  }

  @Test
  void deleteTest() {
    Mockito.doNothing().when(jpaAccount).delete(account);
    accountRepository.delete(account);
    Mockito.verify(jpaAccount, Mockito.times(1)).delete(account);
  }

}
