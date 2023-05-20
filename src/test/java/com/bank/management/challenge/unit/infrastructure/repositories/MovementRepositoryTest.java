package com.bank.management.challenge.unit.infrastructure.repositories;

import com.bank.management.challenge.infrastructure.entities.Movement;
import com.bank.management.challenge.infrastructure.repositories.MovementRepository;
import com.bank.management.challenge.infrastructure.repositories.jpa.JpaMovement;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
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
 * Unit test for MovementRepository.
 *
 * @author jorge-arevalo
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class MovementRepositoryTest {

  @InjectMocks
  MovementRepository movementRepository;

  @Mock
  JpaMovement jpaMovement;

  private Movement movement;

  private UUID movementId;

  private LocalDateTime initialDate;

  private LocalDateTime finalDate;

  private String customerName;

  @BeforeEach
  public void initData() {
    movementId = UUID.randomUUID();
    initialDate = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    finalDate = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    customerName = "Customer Name";
    movement = Movement.builder()
        .id(movementId)
        .movementDate(LocalDateTime.now())
        .movementType("debit")
        .movementValue(BigDecimal.ONE)
        .balance(BigDecimal.ONE)
        .account(null)
        .build();

    log.info("MovementRepositoryTest.initData() - movement: {}", movement);
  }

  @Test
  void saveTest() {
    Mockito.when(jpaMovement.save(movement)).thenReturn(movement);
    var result = movementRepository.save(movement);
    Assertions.assertEquals(movement, result);
  }

  @Test
  void findByIdTest() {
    Mockito.when(jpaMovement.findById(movementId)).thenReturn(Optional.ofNullable(movement));
    var result = movementRepository.findById(movementId);
    var movementResult = result.orElseGet(Movement::new);
    Assertions.assertEquals(movement, movementResult);
  }

  @Test
  void findAllTest() {
    Mockito.when(jpaMovement.findAll()).thenReturn(List.of(movement));
    var result = movementRepository.findAll();
    Assertions.assertEquals(List.of(movement), result);
  }

  @Test
  void findByMovementDateBetweenTest() {
    Mockito.when(jpaMovement.findByMovementDateBetween(initialDate, finalDate, customerName))
        .thenReturn(List.of(movement));
    var result = movementRepository.findByMovementDateBetween(initialDate, finalDate, customerName);
    Assertions.assertEquals(List.of(movement), result);
  }

  @Test
  void deleteTest() {
    Mockito.doNothing().when(jpaMovement).delete(movement);
    movementRepository.delete(movement);
    Mockito.verify(jpaMovement, Mockito.times(1)).delete(movement);
  }

}
