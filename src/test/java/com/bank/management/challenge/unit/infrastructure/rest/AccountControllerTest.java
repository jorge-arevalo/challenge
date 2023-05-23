package com.bank.management.challenge.unit.infrastructure.rest;

import com.bank.management.challenge.domain.models.input.AccountInputDto;
import com.bank.management.challenge.domain.models.output.AccountDto;
import com.bank.management.challenge.domain.ports.services.IAccountService;
import com.bank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import com.bank.management.challenge.infrastructure.rest.AccountController;
import com.bank.management.challenge.infrastructure.rest.input.FormatInput;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;
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
import org.springframework.http.HttpStatus;

/**
 * Unit tests for AccountController.
 *
 * @author jorge-arevalo
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class AccountControllerTest {

  @InjectMocks
  AccountController accountController;

  @Mock
  IAccountService accountService;

  private AccountInputDto accountInput;

  private AccountDto accountDto;

  private String accountId;

  @BeforeEach
  void initData() {
    accountId = UUID.randomUUID().toString();

    accountInput = AccountInputDto.builder()
        .accountNumber("1234567890")
        .accountType("saving")
        .initialBalance(BigDecimal.TEN)
        .status(Boolean.TRUE)
        .customerName("Customer Name")
        .build();

    accountDto = AccountDto.builder()
        .id(accountId)
        .accountNumber(accountInput.getAccountNumber())
        .accountType(accountInput.getAccountType())
        .initialBalance(accountInput.getInitialBalance())
        .status(accountInput.getStatus())
        .customer(null)
        .build();

    log.info("AccountControllerTest.initData() - accountId: {}", accountId);
    log.info("AccountControllerTest.initData() - accountInput: {}", accountInput);
    log.info("AccountControllerTest.initData() - accountDto: {}", accountDto);
  }

  @Test
  void getAllAccountsTest() {
    Mockito.when(accountService.findAll()).thenReturn(Collections.singletonList(accountDto));
    var response = accountController.getAllAccounts();
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertEquals(1, Objects.requireNonNull(response.getBody()).getData().size());
    Assertions.assertEquals(accountId,
        Objects.requireNonNull(response.getBody()).getData().get(0).getId());
  }

  @Test
  void getAccountByIdTest() {
    Mockito.when(accountService.findById(accountId)).thenReturn(accountDto);
    var response = accountController.getAccountById(accountId);
    log.info("Test Response: {}", Objects.requireNonNull(response.getBody()).getData().toString());
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertEquals(String.valueOf(HttpStatus.OK.value()),
        Objects.requireNonNull(response.getBody()).getCode());
    Assertions.assertEquals(HttpStatus.OK.getReasonPhrase(),
        Objects.requireNonNull(response.getBody()).getMessage());
    Assertions.assertEquals(accountId,
        Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void registerAccountTest() {
    FormatInput<AccountInputDto> input = new FormatInput<>();
    input.setData(accountInput);
    Mockito.when(accountService.save(accountInput)).thenReturn(accountDto);
    var response = accountController.registerAccount(input);
    Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
    Assertions.assertEquals(accountId,
        Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void updateAccountTest() {
    FormatInput<AccountInputDto> input = new FormatInput<>();
    input.setData(accountInput);
    Mockito.when(accountService.update(accountId, accountInput)).thenReturn(accountDto);
    var response = accountController.updateAccount(accountId, input);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertEquals(accountId,
        Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void deleteAccountTest() {
    Mockito.doNothing().when(accountService).delete(accountId);
    var response = accountController.deleteAccount(accountId);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertNull(Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void illegalArgumentExceptionTest() {
    accountId = "123";
    IllegalArgumentException iae = Assertions.assertThrows(IllegalArgumentException.class,
        () -> accountController.getAccountById(accountId));
    Assertions.assertEquals(GeneralExceptionMessages.INVALID_ACCOUNT_ID, iae.getMessage());
  }

}
