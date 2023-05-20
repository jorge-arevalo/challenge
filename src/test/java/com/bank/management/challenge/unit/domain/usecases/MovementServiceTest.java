package com.bank.management.challenge.unit.domain.usecases;

import com.bank.management.challenge.domain.models.input.MovementInputDto;
import com.bank.management.challenge.domain.ports.repositories.IAccountRepository;
import com.bank.management.challenge.domain.ports.repositories.IMovementRepository;
import com.bank.management.challenge.domain.usecases.MovementService;
import com.bank.management.challenge.domain.usecases.util.MovementConstants;
import com.bank.management.challenge.infrastructure.config.exception.AccountNotFoundException;
import com.bank.management.challenge.infrastructure.config.exception.InsufficientBalanceException;
import com.bank.management.challenge.infrastructure.config.exception.MovementNotFoundException;
import com.bank.management.challenge.infrastructure.entities.Account;
import com.bank.management.challenge.infrastructure.entities.Movement;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
 * Unit test class for the MovementService class.
 *
 * @author jorge-arevalo
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class MovementServiceTest {

  @InjectMocks
  MovementService movementService;

  @Mock
  IMovementRepository movementRepository;

  @Mock
  IAccountRepository accountRepository;

  @Captor
  ArgumentCaptor<Movement> movementCaptor;

  private MovementInputDto movementInput;

  private Movement movement;

  private Account account;

  private UUID movementId;

  private String accountNumber;

  @BeforeEach
  void initData() {
    movementId = UUID.randomUUID();
    UUID accountId = UUID.randomUUID();
    accountNumber = "123456789";

    movementInput = MovementInputDto.builder()
        .movementType(MovementConstants.MOVEMENT_TYPE_DEBIT)
        .movementValue(BigDecimal.ONE)
        .accountNumber(accountNumber)
        .build();

    account = Account.builder()
        .id(accountId)
        .accountNumber(accountNumber)
        .accountType("SAVINGS")
        .initialBalance(BigDecimal.ONE)
        .status(Boolean.TRUE)
        .customer(null)
        .build();

    movement = Movement.builder()
        .id(movementId)
        .movementDate(LocalDateTime.now())
        .movementType(MovementConstants.MOVEMENT_TYPE_DEBIT)
        .movementValue(BigDecimal.ONE)
        .balance(BigDecimal.ZERO)
        .account(account)
        .build();

    log.info("MovementServiceTest.initData() - movementInput: {}", movementInput);
    log.info("MovementServiceTest.initData() - movement: {}", movement);
  }

  @Test
  void saveTest() {
    Mockito.lenient().when(accountRepository.findByAccountNumber(accountNumber))
        .thenReturn(Optional.of(account));
    Mockito.lenient().when(movementRepository.save(movementCaptor.capture())).thenReturn(movement);
    var result = movementService.save(movementInput);
    Assertions.assertEquals(movementId.toString(), result.getId());
  }

  @Test
  void saveAccountNotFoundTest() {
    Mockito.lenient().when(accountRepository.findByAccountNumber(accountNumber))
        .thenReturn(Optional.empty());
    Assertions.assertThrows(AccountNotFoundException.class, () ->
        movementService.save(movementInput));
  }

  @Test
  void saveMovementTypeCreditTest() {
    movementInput.setMovementType(MovementConstants.MOVEMENT_TYPE_CREDIT);
    movement.setMovementType(MovementConstants.MOVEMENT_TYPE_CREDIT);
    Mockito.lenient().when(accountRepository.findByAccountNumber(accountNumber))
        .thenReturn(Optional.of(account));
    Mockito.lenient().when(movementRepository.save(movementCaptor.capture())).thenReturn(movement);
    var result = movementService.save(movementInput);
    Assertions.assertEquals(movementId.toString(), result.getId());
  }

  @Test
  void saveNotEqualsValuesTest() {
    movementInput.setMovementValue(BigDecimal.TEN);
    Mockito.lenient().when(accountRepository.findByAccountNumber(accountNumber))
        .thenReturn(Optional.of(account));
    Mockito.lenient().when(movementRepository.save(movementCaptor.capture())).thenReturn(movement);
    Assertions.assertThrows(InsufficientBalanceException.class, () ->
        movementService.save(movementInput));
  }

  @Test
  void saveNotBalanceTest() {
    movementInput.setMovementValue(BigDecimal.TEN);
    account.setInitialBalance(BigDecimal.ZERO);
    movement.setAccount(account);
    Mockito.lenient().when(accountRepository.findByAccountNumber(accountNumber))
        .thenReturn(Optional.of(account));
    Assertions.assertThrows(InsufficientBalanceException.class, () ->
        movementService.save(movementInput));
  }

  @Test
  void findByIdTest() {
    Mockito.lenient().when(movementRepository.findById(movementId))
        .thenReturn(Optional.of(movement));
    var result = movementService.findById(movementId.toString());
    Assertions.assertEquals(movementId.toString(), result.getId());
  }

  @Test
  void findByIdNotFoundTest() {
    var movementIdStr = movementId.toString();
    Mockito.lenient().when(movementRepository.findById(movementId))
        .thenReturn(Optional.empty());
    Assertions.assertThrows(MovementNotFoundException.class, () ->
        movementService.findById(movementIdStr));
  }

  @Test
  void findAllTest() {
    Mockito.lenient().when(movementRepository.findAll()).thenReturn(List.of(movement));
    var result = movementService.findAll();
    Assertions.assertEquals(movementId.toString(), result.get(0).getId());
  }

  @Test
  void findAllEmptyTest() {
    Mockito.lenient().when(movementRepository.findAll()).thenReturn(List.of());
    Assertions.assertThrows(MovementNotFoundException.class, () -> movementService.findAll());
  }

  @Test
  void updateTest() {
    Mockito.lenient().when(movementRepository.findById(movementId))
        .thenReturn(Optional.of(movement));
    Mockito.lenient().when(movementRepository.save(movementCaptor.capture())).thenReturn(movement);
    var result = movementService.update(movementId.toString(), movementInput);
    Assertions.assertEquals(movementId.toString(), result.getId());
  }

  @Test
  void updateNotFoundTest() {
    var movementIdStr = movementId.toString();
    Mockito.lenient().when(movementRepository.findById(movementId))
        .thenReturn(Optional.empty());
    Assertions.assertThrows(MovementNotFoundException.class, () ->
        movementService.update(movementIdStr, movementInput));
  }

  @Test
  void updateMovementTypeCreditTest() {
    movementInput.setMovementType(MovementConstants.MOVEMENT_TYPE_CREDIT);
    movement.setMovementType(MovementConstants.MOVEMENT_TYPE_CREDIT);
    Mockito.lenient().when(movementRepository.findById(movementId))
        .thenReturn(Optional.of(movement));
    Mockito.lenient().when(movementRepository.save(movementCaptor.capture())).thenReturn(movement);
    var result = movementService.update(movementId.toString(), movementInput);
    Assertions.assertEquals(movementId.toString(), result.getId());
  }

  @Test
  void deleteTest() {
    Mockito.lenient().when(movementRepository.findById(movementId))
        .thenReturn(Optional.of(movement));
    Mockito.lenient().doNothing().when(movementRepository).delete(movement);
    movementService.delete(movementId.toString());
    Mockito.verify(movementRepository, Mockito.times(1)).delete(movement);
  }

  @Test
  void deleteNotFoundTest() {
    var movementIdStr = movementId.toString();
    Mockito.lenient().when(movementRepository.findById(movementId))
        .thenReturn(Optional.empty());
    Assertions.assertThrows(MovementNotFoundException.class, () ->
        movementService.delete(movementIdStr));
  }

}
