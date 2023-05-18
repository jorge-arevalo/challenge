package com.bank.management.challenge.infrastructure.config.exception;

/**
 * Exception for movement not found.
 *
 * @author jorge-arevalo
 */
public class MovementNotFoundException extends RuntimeException {

  public MovementNotFoundException(String message) {
    super(message);
  }

}
