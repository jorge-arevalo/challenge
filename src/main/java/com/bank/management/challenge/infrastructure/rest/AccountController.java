package com.bank.management.challenge.infrastructure.rest;

import com.bank.management.challenge.domain.models.input.AccountInputDto;
import com.bank.management.challenge.domain.models.output.AccountDto;
import com.bank.management.challenge.domain.ports.services.IAccountService;
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
 * Controller to manage the account operations.
 *
 * @author jorge-arevalo
 */
@RestController
@Validated
@RequestMapping("/v1/accounts")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Accounts operations")
public class AccountController {

  private final IAccountService accountService;

  @GetMapping
  @Operation(description = "Gets all accounts")
  public ResponseEntity<FormatOutput<List<AccountDto>>> getAllAccounts() {
    log.info("Getting all accounts");
    HttpStatus status = HttpStatus.OK;
    FormatOutput<List<AccountDto>> output = new FormatOutput<>();
    output.setData(accountService.findAll());
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @GetMapping("/{id}")
  @Operation(description = "Gets an account by id")
  public ResponseEntity<FormatOutput<AccountDto>> getAccountById(@PathVariable("id") String id) {
    validateId(id);
    log.info("Getting account by id: {}", id);
    HttpStatus status = HttpStatus.OK;
    FormatOutput<AccountDto> output = new FormatOutput<>();
    output.setData(accountService.findById(id));
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @PostMapping
  @Operation(description = "Registers a new account")
  public ResponseEntity<FormatOutput<AccountDto>> registerAccount(
      @Valid @RequestBody FormatInput<AccountInputDto> accountInput) {
    log.info("Registering a new account");
    HttpStatus status = HttpStatus.CREATED;
    FormatOutput<AccountDto> output = new FormatOutput<>();
    output.setData(accountService.save(accountInput.getData()));
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @PutMapping("/{id}")
  @Operation(description = "Updates an account by id")
  public ResponseEntity<FormatOutput<AccountDto>> updateAccount(
      @PathVariable("id") @NotNull String id,
      @Valid @RequestBody FormatInput<AccountInputDto> accountInput) {
    validateId(id);
    log.info("Updating account by id: {}", id);
    HttpStatus status = HttpStatus.OK;
    FormatOutput<AccountDto> output = new FormatOutput<>();
    output.setData(accountService.update(id, accountInput.getData()));
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  @DeleteMapping("/{id}")
  @Operation(description = "Deletes an account by id")
  public ResponseEntity<FormatOutput<AccountDto>> deleteAccount(@PathVariable("id") String id) {
    validateId(id);
    log.info("Deleting account by id: {}", id);
    HttpStatus status = HttpStatus.OK;
    FormatOutput<AccountDto> output = new FormatOutput<>();
    accountService.delete(id);
    output.setData(AccountDto.builder().build());
    output.setCode(String.valueOf(status.value()));
    output.setMessage(status.getReasonPhrase());
    return new ResponseEntity<>(output, status);
  }

  private void validateId(String id) {
    try {
      log.info("Validating customer id: {}", UUID.fromString(id));
    } catch (IllegalArgumentException iex) {
      log.error(iex.getMessage(), iex);
      throw new IllegalArgumentException("Invalid customer id");
    }
  }

}
