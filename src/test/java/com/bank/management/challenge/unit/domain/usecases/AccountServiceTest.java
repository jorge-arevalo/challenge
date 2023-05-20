package com.bank.management.challenge.unit.domain.usecases;

import com.bank.management.challenge.domain.models.input.AccountInputDto;
import com.bank.management.challenge.domain.ports.repositories.IAccountRepository;
import com.bank.management.challenge.domain.ports.repositories.ICustomerRepository;
import com.bank.management.challenge.domain.usecases.AccountService;
import com.bank.management.challenge.infrastructure.config.exception.AccountNotFoundException;
import com.bank.management.challenge.infrastructure.config.exception.CustomerNotFoundException;
import com.bank.management.challenge.infrastructure.entities.Account;
import com.bank.management.challenge.infrastructure.entities.Customer;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test class for the AccountService class.
 *
 * @author jorge-arevalo
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class AccountServiceTest {

  @InjectMocks
  AccountService accountService;

  @Mock
  IAccountRepository accountRepository;

  @Mock
  ICustomerRepository customerRepository;

  @Captor
  ArgumentCaptor<Account> accountCaptor;

  private AccountInputDto accountInput;

  private Account account;

  private Customer customer;

  private UUID accountId;

  private String customerName;

  @BeforeEach
  void initData() {
    accountId = UUID.randomUUID();
    UUID customerId = UUID.randomUUID();
    customerName = "Customer Name";

    accountInput = AccountInputDto.builder()
        .accountNumber("123456789")
        .accountType("SAVINGS")
        .initialBalance(BigDecimal.ONE)
        .customerName(customerName)
        .status(Boolean.TRUE)
        .build();

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

    account = Account.builder()
        .id(accountId)
        .accountNumber(accountInput.getAccountNumber())
        .accountType(accountInput.getAccountType())
        .initialBalance(accountInput.getInitialBalance())
        .status(Boolean.TRUE)
        .customer(customer)
        .build();

    log.info("AccountServiceTest.initData() - accountInput: {}", accountInput);
    log.info("AccountServiceTest.initData() - account: {}", account);
  }

  @Test
  void saveTest() {
    Mockito.lenient().when(customerRepository.findByName(customerName))
        .thenReturn(Optional.of(customer));
    Mockito.lenient().when(accountRepository.save(accountCaptor.capture())).thenReturn(account);
    var result = accountService.save(accountInput);
    Assertions.assertEquals(accountId.toString(), result.getId());
  }

  @Test
  void saveCustomerNotFoundTest() {
    Mockito.lenient().when(customerRepository.findByName(customerName))
        .thenReturn(Optional.empty());
    Assertions.assertThrows(CustomerNotFoundException.class, () ->
        accountService.save(accountInput));
  }

  @Test
  void findByIdTest() {
    Mockito.lenient().when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
    var result = accountService.findById(accountId.toString());
    Assertions.assertEquals(accountId.toString(), result.getId());
  }

  @Test
  void findByIdNotFoundTest() {
    var accountIdStr = accountId.toString();
    Mockito.lenient().when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
    Assertions.assertThrows(AccountNotFoundException.class, () ->
        accountService.findById(accountIdStr));
  }

  @Test
  void findAllTest() {
    Mockito.lenient().when(accountRepository.findAll()).thenReturn(List.of(account));
    var result = accountService.findAll();
    Assertions.assertEquals(accountId.toString(), result.get(0).getId());
  }

  @Test
  void findAllEmptyTest() {
    Mockito.lenient().when(accountRepository.findAll()).thenReturn(List.of());
    Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.findAll());
  }

  @Test
  void updateTest() {
    Mockito.lenient().when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
    Mockito.lenient().when(accountRepository.save(accountCaptor.capture())).thenReturn(account);
    var result = accountService.update(accountId.toString(), accountInput);
    Assertions.assertEquals(accountId.toString(), result.getId());
  }

  @Test
  void updateNotFoundTest() {
    var accountIdStr = accountId.toString();
    Mockito.lenient().when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
    Assertions.assertThrows(AccountNotFoundException.class, () ->
        accountService.update(accountIdStr, accountInput));
  }

  @Test
  void deleteTest() {
    Mockito.lenient().when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
    accountService.delete(accountId.toString());
    Mockito.verify(accountRepository, Mockito.times(1)).delete(account);
  }

  @Test
  void deleteNotFoundTest() {
    var accountIdStr = accountId.toString();
    Mockito.lenient().when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
    Assertions.assertThrows(AccountNotFoundException.class, () ->
        accountService.delete(accountIdStr));
  }

}
