package com.bank.management.challenge.infrastructure.config.exception;

/**
 * Exception for the customer not found.
 *
 * @author jorge-arevalo
 */
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
      super(message);
    }

}
