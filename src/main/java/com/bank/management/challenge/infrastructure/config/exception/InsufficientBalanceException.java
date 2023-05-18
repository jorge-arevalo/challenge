package com.bank.management.challenge.infrastructure.config.exception;

/**
 * Exception for not available balance.
 *
 * @author jorge-arevalo
 */
public class InsufficientBalanceException extends RuntimeException {

      public InsufficientBalanceException(String message) { super(message); }

}
