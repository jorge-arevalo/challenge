package com.bank.management.challenge.unit.infrastructure.rest;

import com.bank.management.challenge.domain.models.input.MovementInputDto;
import com.bank.management.challenge.domain.models.output.MovementDto;
import com.bank.management.challenge.domain.ports.services.IMovementService;
import com.bank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import com.bank.management.challenge.infrastructure.rest.MovementController;
import com.bank.management.challenge.infrastructure.rest.input.FormatInput;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
 * Unit tests for MovementController.
 *
 * @author jorge-arevalo
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class MovementControllerTest {

  @InjectMocks
  MovementController movementController;

  @Mock
  IMovementService movementService;

  private MovementInputDto movementInput;

  private MovementDto movementDto;

  private String movementId;

  @BeforeEach
  void initData() {
    movementId = UUID.randomUUID().toString();

    movementInput = MovementInputDto.builder()
        .movementType("debit")
        .movementValue(BigDecimal.TEN)
        .accountNumber("1234567890")
        .build();

    movementDto = MovementDto.builder()
        .id(movementId)
        .movementDate(LocalDateTime.now().toString())
        .movementType(movementInput.getMovementType())
        .movementValue(movementInput.getMovementValue())
        .balance(BigDecimal.TEN)
        .account(null)
        .build();

    log.info("MovementControllerTest.initData() - movementId: {}", movementId);
    log.info("MovementControllerTest.initData() - movementInput: {}", movementInput);
    log.info("MovementControllerTest.initData() - movementDto: {}", movementDto);
  }

  @Test
  void getAllMovementsTest() {
    Mockito.when(movementService.findAll()).thenReturn(Collections.singletonList(movementDto));
    var response = movementController.getAllMovements();
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertEquals(1, Objects.requireNonNull(response.getBody()).getData().size());
    Assertions.assertEquals(movementId,
        Objects.requireNonNull(response.getBody()).getData().get(0).getId());
  }

  @Test
  void getMovementByIdTest() {
    Mockito.when(movementService.findById(movementId)).thenReturn(movementDto);
    var response = movementController.getMovementById(movementId);
    log.info("Test Response: {}", Objects.requireNonNull(response.getBody()).getData().toString());
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertEquals(movementId,
        Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void registerMovementTest() {
    FormatInput<MovementInputDto> input = new FormatInput<>();
    input.setData(movementInput);
    Mockito.when(movementService.save(movementInput)).thenReturn(movementDto);
    var response = movementController.registerMovement(input);
    Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
    Assertions.assertEquals(movementId,
        Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void updateMovementTest() {
    FormatInput<MovementInputDto> input = new FormatInput<>();
    input.setData(movementInput);
    Mockito.when(movementService.update(movementId, movementInput)).thenReturn(movementDto);
    var response = movementController.updateMovement(movementId, input);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertEquals(movementId,
        Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void deleteMovementTest() {
    Mockito.doNothing().when(movementService).delete(movementId);
    var response = movementController.deleteMovement(movementId);
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    Assertions.assertNull(Objects.requireNonNull(response.getBody()).getData().getId());
  }

  @Test
  void illegalArgumentExceptionTest() {
    movementId = "123";
    IllegalArgumentException iae = Assertions.assertThrows(IllegalArgumentException.class,
        () -> movementController.getMovementById(movementId));
    Assertions.assertEquals(GeneralExceptionMessages.INVALID_MOVEMENT_ID, iae.getMessage());
  }

}
