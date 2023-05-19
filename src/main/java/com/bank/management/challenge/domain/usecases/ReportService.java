package com.bank.management.challenge.domain.usecases;

import com.bank.management.challenge.domain.models.output.ReportDto;
import com.bank.management.challenge.domain.ports.repositories.IMovementRepository;
import com.bank.management.challenge.domain.ports.services.IReportService;
import com.bank.management.challenge.domain.usecases.util.MovementConstants;
import com.bank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import com.bank.management.challenge.infrastructure.config.exception.MovementNotFoundException;
import com.bank.management.challenge.infrastructure.entities.Movement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing the reports.
 *
 * @author jorge-arevalo
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReportService implements IReportService {

  private final IMovementRepository movementRepository;

  @Override
  public List<ReportDto> findByMovementDateBetween(String initialDate, String finalDate,
      String customerName) {
    final var dateFormatter = DateTimeFormatter.ofPattern(MovementConstants.DATE_FORMATTER);
    var startDate = LocalDate.parse(initialDate, dateFormatter).atStartOfDay();
    var endDate = LocalDate.parse(finalDate, dateFormatter).atStartOfDay().with(LocalTime.MAX);
    var movementList = movementRepository.findByMovementDateBetween(startDate, endDate,
        String.format("%s%s%s", MovementConstants.LIKE, customerName, MovementConstants.LIKE));
    if (movementList.isEmpty()) {
      throw new MovementNotFoundException(GeneralExceptionMessages.MOVEMENTS_NOT_FOUND);
    }
    return movementList.stream().map(this::mapToReportDto).collect(Collectors.toList());
  }

  private ReportDto mapToReportDto(Movement movement) {
    var initialBalance = calculateInitialBalance(movement);
    var movementValue = calculateMovementValue(movement);
    return ReportDto.builder()
        .movementDate(movement.getMovementDate().format(DateTimeFormatter.ofPattern(
            MovementConstants.DATE_FORMATTER)))
        .customerName(movement.getAccount().getCustomer().getName())
        .accountNumber(movement.getAccount().getAccountNumber())
        .accountType(movement.getAccount().getAccountType())
        .initialBalance(initialBalance)
        .movementValue(movementValue)
        .balance(calculateBalance(initialBalance, movementValue))
        .build();
  }

  private BigDecimal calculateInitialBalance(Movement movement) {
    return movement.getMovementType().equals(MovementConstants.MOVEMENT_TYPE_DEBIT) ?
        movement.getAccount().getInitialBalance().add(movement.getMovementValue()) :
        movement.getAccount().getInitialBalance().subtract(movement.getMovementValue());
  }

  private BigDecimal calculateMovementValue(Movement movement) {
    return movement.getMovementType().equals(MovementConstants.MOVEMENT_TYPE_DEBIT) ?
        movement.getMovementValue().multiply(BigDecimal.valueOf(-1)) :
        movement.getMovementValue();
  }

  private BigDecimal calculateBalance(BigDecimal balance, BigDecimal movementValue) {
    return balance.add(movementValue);
  }

}
