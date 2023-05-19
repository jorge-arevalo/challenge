package com.bank.management.challenge.domain.usecases;

import com.bank.management.challenge.domain.models.input.MovementInputDto;
import com.bank.management.challenge.domain.models.output.AccountDto;
import com.bank.management.challenge.domain.models.output.MovementDto;
import com.bank.management.challenge.domain.ports.repositories.IAccountRepository;
import com.bank.management.challenge.domain.ports.repositories.IMovementRepository;
import com.bank.management.challenge.domain.ports.services.IMovementService;
import com.bank.management.challenge.domain.usecases.util.MovementConstants;
import com.bank.management.challenge.infrastructure.config.exception.AccountNotFoundException;
import com.bank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import com.bank.management.challenge.infrastructure.config.exception.InsufficientBalanceException;
import com.bank.management.challenge.infrastructure.config.exception.MovementNotFoundException;
import com.bank.management.challenge.infrastructure.entities.Movement;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing the movements.
 *
 * @author jorge-arevalo
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MovementService implements IMovementService {

  private final IAccountRepository accountRepository;
  private final IMovementRepository movementRepository;

  @Override
  public MovementDto save(MovementInputDto movementInput) {
    var account = accountRepository.findByAccountNumber(movementInput.getAccountNumber()).orElseThrow(
        () -> new AccountNotFoundException(GeneralExceptionMessages.ACCOUNT_NOT_FOUND));
    var movement = Movement.builder()
        .movementDate(LocalDateTime.now())
        .movementType(movementInput.getMovementType())
        .movementValue(movementInput.getMovementValue().setScale(2, RoundingMode.HALF_UP))
        .account(account)
        .build();
    calculateBalance(movement);
    return mapToMovementDto(movementRepository.save(movement));
  }

  @Override
  @Transactional(readOnly = true)
  public MovementDto findById(String id) {
    var movement = movementRepository.findById(UUID.fromString(id)).orElseThrow(
        () -> new MovementNotFoundException(GeneralExceptionMessages.MOVEMENT_NOT_FOUND));
    return mapToMovementDto(movement);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MovementDto> findAll() {
    var movementList = movementRepository.findAll();
    if (movementList.isEmpty()) {
      throw new MovementNotFoundException(GeneralExceptionMessages.MOVEMENTS_NOT_FOUND);
    }
    return movementList.stream().map(this::mapToMovementDto).collect(Collectors.toList());
  }

  @Override
  public MovementDto update(String id, MovementInputDto movementInput) {
    var movement = movementRepository.findById(UUID.fromString(id)).orElseThrow(
        () -> new MovementNotFoundException(GeneralExceptionMessages.MOVEMENT_NOT_FOUND));
    calculateRollBackBalance(movement);
    movement.setMovementType(movementInput.getMovementType());
    movement.setMovementValue(movementInput.getMovementValue().setScale(2, RoundingMode.HALF_UP));
    calculateBalance(movement);
    return mapToMovementDto(movementRepository.save(movement));
  }

  @Override
  public void delete(String id) {
    var movement = movementRepository.findById(UUID.fromString(id)).orElseThrow(
        () -> new MovementNotFoundException(GeneralExceptionMessages.MOVEMENT_NOT_FOUND));
    calculateRollBackBalance(movement);
    accountRepository.save(movement.getAccount());
    movementRepository.delete(movement);
  }

  private MovementDto mapToMovementDto(Movement movement) {
    var account = AccountDto.builder()
        .id(movement.getAccount().getId().toString())
        .accountNumber(movement.getAccount().getAccountNumber())
        .accountType(movement.getAccount().getAccountType())
        .initialBalance(movement.getAccount().getInitialBalance())
        .status(movement.getAccount().getStatus())
        .build();
    final var dateFormatter = DateTimeFormatter.ofPattern(MovementConstants.DATE_FORMATTER);
    return MovementDto.builder()
        .id(movement.getId().toString())
        .movementDate(movement.getMovementDate().format(dateFormatter))
        .movementType(movement.getMovementType())
        .movementValue(movement.getMovementValue().setScale(2, RoundingMode.HALF_UP))
        .balance(movement.getBalance().setScale(2, RoundingMode.HALF_UP))
        .account(account)
        .build();
  }

  private void calculateBalance(Movement movement) {
    if (movement.getMovementType().equals(MovementConstants.MOVEMENT_TYPE_DEBIT)) {
      if (movement.getAccount().getInitialBalance().compareTo(BigDecimal.ZERO) > 0
          && movement.getAccount().getInitialBalance().compareTo(movement.getMovementValue())
          >= 0) {
        movement.setBalance(
            movement.getAccount().getInitialBalance().subtract(movement.getMovementValue()));
      } else {
        throw new InsufficientBalanceException(GeneralExceptionMessages.INSUFFICIENT_BALANCE);
      }
    } else if (movement.getMovementType().equals(MovementConstants.MOVEMENT_TYPE_CREDIT)) {
      movement.setBalance(
          movement.getAccount().getInitialBalance().add(movement.getMovementValue()));
    }
    movement.getAccount().setInitialBalance(movement.getBalance());
  }

  private void calculateRollBackBalance(Movement movement) {
    if (movement.getMovementType().equals(MovementConstants.MOVEMENT_TYPE_DEBIT)) {
      movement.setBalance(
          movement.getAccount().getInitialBalance().add(movement.getMovementValue()));
    } else if (movement.getMovementType().equals(MovementConstants.MOVEMENT_TYPE_CREDIT)) {
      movement.setBalance(
          movement.getAccount().getInitialBalance().subtract(movement.getMovementValue()));
    }
    movement.getAccount().setInitialBalance(movement.getBalance());
  }

}
