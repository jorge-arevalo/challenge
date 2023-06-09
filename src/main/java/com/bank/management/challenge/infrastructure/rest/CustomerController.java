package com.bank.management.challenge.infrastructure.rest;

import com.bank.management.challenge.domain.models.input.CustomerInputDto;
import com.bank.management.challenge.domain.models.output.CustomerDto;
import com.bank.management.challenge.domain.ports.services.ICustomerService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to manage the customer operations.
 *
 * @author jorge-arevalo
 */
@RestController
@Validated
@RequestMapping("/v1/customers")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Customers operations")
public class CustomerController {

  private final ICustomerService customerService;

  @GetMapping
  @Operation(description = "Gets all customers")
  public ResponseEntity<FormatOutput<List<CustomerDto>>> getAllCustomers() {
    log.info("Getting all customers");
    HttpStatus status = HttpStatus.OK;
    FormatOutput<List<CustomerDto>> output = new FormatOutput<>();
    output.setData(customerService.findAll());
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @GetMapping("/{id}")
  @Operation(description = "Gets a customer by id")
  public ResponseEntity<FormatOutput<CustomerDto>> getCustomerById(@PathVariable("id") String id) {
    validateId(id);
    log.info("Getting customer by id: {}", id);
    HttpStatus status = HttpStatus.OK;
    FormatOutput<CustomerDto> output = new FormatOutput<>();
    output.setData(customerService.findById(id));
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @PostMapping
  @Operation(description = "Registers a new customer")
  public ResponseEntity<FormatOutput<CustomerDto>> registerCustomer(
      @Valid @RequestBody FormatInput<CustomerInputDto> customerInput) {
    log.info("Registering a new customer");
    HttpStatus status = HttpStatus.CREATED;
    FormatOutput<CustomerDto> output = new FormatOutput<>();
    output.setData(customerService.save(customerInput.getData()));
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @PutMapping("/{id}")
  @Operation(description = "Updates a customer by id")
  public ResponseEntity<FormatOutput<CustomerDto>> updateCustomer(
      @PathVariable("id") @NotNull String id,
      @Valid @RequestBody FormatInput<CustomerInputDto> customerInput) {
    validateId(id);
    log.info("Updating customer by id: {}", id);
    HttpStatus status = HttpStatus.OK;
    FormatOutput<CustomerDto> output = new FormatOutput<>();
    output.setData(customerService.update(id, customerInput.getData()));
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @DeleteMapping("/{id}")
  @Operation(description = "Deletes a customer by id")
  public ResponseEntity<FormatOutput<CustomerDto>> deleteCustomer(@PathVariable("id") String id) {
    validateId(id);
    log.info("Deleting customer by id: {}", id);
    HttpStatus status = HttpStatus.OK;
    FormatOutput<CustomerDto> output = new FormatOutput<>();
    customerService.delete(id);
    output.setData(CustomerDto.builder().build());
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  private void validateId(String id) {
    try {
      log.info("Validating customer id: {}", UUID.fromString(id));
    } catch (IllegalArgumentException iex) {
      log.error(iex.getMessage(), iex);
      throw new IllegalArgumentException(GeneralExceptionMessages.INVALID_CUSTOMER_ID);
    }
  }

}
