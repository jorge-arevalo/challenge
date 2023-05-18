package com.bank.management.challenge.infrastructure.config.exception;

/**
 * Exception for account not found.
 *
 * @author jorge-arevalo
 */
public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(String message) {
    super(message);
  }

}
