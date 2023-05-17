package com.bank.management.challenge.infrastructure.config.exception;

/**
 * Code description
 *
 * @author jorge-arevalo
 */
public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(String message) {
    super(message);
  }

}
