package com.bank.management.challenge.unit.domain.usecases;

import com.bank.management.challenge.domain.ports.repositories.IMovementRepository;
import com.bank.management.challenge.domain.usecases.ReportService;
import com.bank.management.challenge.domain.usecases.util.MovementConstants;
import com.bank.management.challenge.infrastructure.config.exception.MovementNotFoundException;
import com.bank.management.challenge.infrastructure.entities.Account;
import com.bank.management.challenge.infrastructure.entities.Customer;
import com.bank.management.challenge.infrastructure.entities.Movement;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
 * Unit test class for the ReportService class.
 *
 * @author jorge-arevalo
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class ReportServiceTest {

  @InjectMocks
  ReportService reportService;

  @Mock
  IMovementRepository movementRepository;

  @Captor
  ArgumentCaptor<LocalDateTime> initialDateCaptor;

  @Captor
  ArgumentCaptor<LocalDateTime> finalDateCaptor;

  @Captor
  ArgumentCaptor<String> customerNameCaptor;

  private Movement movement;

  private String initialDate;

  private String finalDate;

  private String customerName;

  @BeforeEach
  void initData() {
    initialDate = "01/01/2021";
    finalDate = "31/01/2021";
    customerName = "Customer Name";
    UUID movementId = UUID.randomUUID();

    var customer = Customer.builder()
        .id(UUID.randomUUID())
        .name(customerName)
        .build();

    var account = Account.builder()
        .id(UUID.randomUUID())
        .accountNumber("123456789")
        .accountType("SAVINGS")
        .initialBalance(BigDecimal.ONE)
        .customer(customer)
        .build();

    movement = Movement.builder()
        .id(movementId)
        .movementDate(LocalDateTime.now())
        .movementType(MovementConstants.MOVEMENT_TYPE_DEBIT)
        .movementValue(BigDecimal.ONE)
        .balance(BigDecimal.ZERO)
        .account(account)
        .build();

    log.info("ReportServiceTest.initData() - movement: {}", movement);
  }

  @Test
  void findByMovementDateBetweenTest() {
    Mockito.lenient().when(movementRepository.findByMovementDateBetween(initialDateCaptor.capture(),
        finalDateCaptor.capture(), customerNameCaptor.capture())).thenReturn(List.of(movement));
    var result = reportService.findByMovementDateBetween(initialDate, finalDate, customerName);
    Assertions.assertEquals(BigDecimal.valueOf(-1), result.get(0).getMovementValue());
  }

  @Test
  void findByMovementDateBetweenMovementCreditTest() {
    movement.setMovementType(MovementConstants.MOVEMENT_TYPE_CREDIT);
    Mockito.lenient().when(movementRepository.findByMovementDateBetween(initialDateCaptor.capture(),
        finalDateCaptor.capture(), customerNameCaptor.capture())).thenReturn(List.of(movement));
    var result = reportService.findByMovementDateBetween(initialDate, finalDate, customerName);
    Assertions.assertEquals(BigDecimal.ONE, result.get(0).getMovementValue());
  }

  @Test
  void findByMovementDateBetweenEmptyTest() {
    Mockito.lenient().when(movementRepository.findByMovementDateBetween(initialDateCaptor.capture(),
        finalDateCaptor.capture(), customerNameCaptor.capture())).thenReturn(List.of());
    Assertions.assertThrows(MovementNotFoundException.class, () ->
        reportService.findByMovementDateBetween(initialDate, finalDate, customerName));
  }

}
