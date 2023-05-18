package com.bank.management.challenge.domain.usecases;

import com.bank.management.challenge.domain.models.input.AccountInputDto;
import com.bank.management.challenge.domain.models.output.AccountDto;
import com.bank.management.challenge.domain.models.output.CustomerDto;
import com.bank.management.challenge.domain.ports.repositories.IAccountRepository;
import com.bank.management.challenge.domain.ports.repositories.ICustomerRepository;
import com.bank.management.challenge.domain.ports.services.IAccountService;
import com.bank.management.challenge.infrastructure.config.exception.AccountNotFoundException;
import com.bank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import com.bank.management.challenge.infrastructure.entities.Account;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing the accounts.
 *
 * @author jorge-arevalo
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService implements IAccountService {

  private final IAccountRepository accountRepository;
  private final ICustomerRepository customerRepository;

  @Override
  public AccountDto save(AccountInputDto accountInput) {
    var customer = customerRepository.findByName(accountInput.getCustomerName()).orElseThrow(
        () -> new AccountNotFoundException(GeneralExceptionMessages.CUSTOMER_NOT_FOUND));
    var account = Account.builder()
        .accountNumber(accountInput.getAccountNumber())
        .accountType(accountInput.getAccountType())
        .initialBalance(accountInput.getInitialBalance())
        .status(Boolean.TRUE)
        .customer(customer)
        .build();
    return mapToAccountDto(accountRepository.save(account));
  }

  @Override
  @Transactional(readOnly = true)
  public AccountDto findById(String id) {
    var account = accountRepository.findById(UUID.fromString(id)).orElseThrow(
        () -> new AccountNotFoundException(GeneralExceptionMessages.ACCOUNT_NOT_FOUND));
    return mapToAccountDto(account);
  }

  @Override
  @Transactional(readOnly = true)
  public List<AccountDto> findAll() {
    var accountList = accountRepository.findAll();
    if (accountList.isEmpty()) {
      throw new AccountNotFoundException(GeneralExceptionMessages.ACCOUNTS_NOT_FOUND);
    }
    return accountList.stream().map(this::mapToAccountDto).collect(Collectors.toList());
  }

  @Override
  public AccountDto update(String id, AccountInputDto accountInput) {
    var account = accountRepository.findById(UUID.fromString(id)).orElseThrow(
        () -> new AccountNotFoundException(GeneralExceptionMessages.ACCOUNT_NOT_FOUND));
    account.setAccountNumber(accountInput.getAccountNumber());
    account.setAccountType(accountInput.getAccountType());
    account.setInitialBalance(accountInput.getInitialBalance());
    account.setStatus(accountInput.getStatus());
    return mapToAccountDto(accountRepository.save(account));
  }

  @Override
  public void delete(String id) {
    var account = accountRepository.findById(UUID.fromString(id)).orElseThrow(
        () -> new AccountNotFoundException(GeneralExceptionMessages.ACCOUNT_NOT_FOUND));
    accountRepository.delete(account);
  }

  private AccountDto mapToAccountDto(Account account) {
    var customer = CustomerDto.builder()
        .id(account.getCustomer().getId().toString())
        .name(account.getCustomer().getName())
        .gender(account.getCustomer().getGender())
        .age(account.getCustomer().getAge())
        .identification(account.getCustomer().getIdentification())
        .address(account.getCustomer().getAddress())
        .phoneNumber(account.getCustomer().getPhoneNumber())
        .status(account.getCustomer().getStatus())
        .build();
    return AccountDto.builder()
        .id(account.getId().toString())
        .accountNumber(account.getAccountNumber())
        .accountType(account.getAccountType())
        .initialBalance(account.getInitialBalance().setScale(2, RoundingMode.HALF_UP))
        .status(account.getStatus())
        .customer(customer)
        .build();
  }

}
