package com.bank.management.challenge.infrastructure.rest;

import com.bank.management.challenge.domain.models.input.MovementInputDto;
import com.bank.management.challenge.domain.models.output.MovementDto;
import com.bank.management.challenge.domain.ports.services.IMovementService;
import com.bank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import com.bank.management.challenge.infrastructure.rest.input.FormatInput;
import com.bank.management.challenge.infrastructure.rest.output.FormatOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for manage the movement operations.
 *
 * @author jorge-arevalo
 */
@RestController
@Validated
@RequestMapping("/v1/movements")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Movements", description = "Movements operations")
public class MovementController {

  private final IMovementService movementService;

  @GetMapping
  @Operation(description = "Gets all movements")
  public ResponseEntity<FormatOutput<List<MovementDto>>> getAllMovements() {
    log.info("Getting all movements");
    HttpStatus status = HttpStatus.OK;
    FormatOutput<List<MovementDto>> output = new FormatOutput<>();
    output.setData(movementService.findAll());
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @GetMapping("/{id}")
  @Operation(description = "Gets a movement by id")
  public ResponseEntity<FormatOutput<MovementDto>> getMovementById(@PathVariable("id") String id) {
    validateId(id);
    log.info("Getting movement by id: {}", id);
    HttpStatus status = HttpStatus.OK;
    FormatOutput<MovementDto> output = new FormatOutput<>();
    output.setData(movementService.findById(id));
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @PostMapping
  @Operation(description = "Registers a new movement")
  public ResponseEntity<FormatOutput<MovementDto>> registerMovement(
      @Valid @RequestBody FormatInput<MovementInputDto> movementInput) {
    log.info("Registering a new movement");
    HttpStatus status = HttpStatus.CREATED;
    FormatOutput<MovementDto> output = new FormatOutput<>();
    output.setData(movementService.save(movementInput.getData()));
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @PutMapping("/{id}")
  @Operation(description = "Updates a movement by id")
  public ResponseEntity<FormatOutput<MovementDto>> updateMovement(
      @PathVariable("id") @NotNull String id,
      @Valid @RequestBody FormatInput<MovementInputDto> movementInput) {
    validateId(id);
    log.info("Updating movement by id: {}", id);
    HttpStatus status = HttpStatus.OK;
    FormatOutput<MovementDto> output = new FormatOutput<>();
    output.setData(movementService.update(id, movementInput.getData()));
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @DeleteMapping("/{id}")
  @Operation(description = "Deletes a movement by id")
  public ResponseEntity<FormatOutput<MovementDto>> deleteMovement(@PathVariable("id") String id) {
    validateId(id);
    log.info("Deleting movement by id: {}", id);
    HttpStatus status = HttpStatus.OK;
    FormatOutput<MovementDto> output = new FormatOutput<>();
    movementService.delete(id);
    output.setData(MovementDto.builder().build());
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  private void validateId(String id) {
    try {
      log.info("Validating customer id: {}", UUID.fromString(id));
    } catch (IllegalArgumentException iex) {
      log.error(iex.getMessage(), iex);
      throw new IllegalArgumentException(GeneralExceptionMessages.INVALID_MOVEMENT_ID);
    }
  }

}
