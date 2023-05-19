package com.bank.management.challenge.domain.usecases.util;

/**
 * Constants for the movements.
 *
 * @author jorge-arevalo
 */
public class MovementConstants {

  public static final String DATE_FORMATTER = "dd/MM/yyyy";
  public static final String MOVEMENT_TYPE_DEBIT = "Debit";
  public static final String MOVEMENT_TYPE_CREDIT = "Credit";
  public static final String LIKE = "%";

  private MovementConstants() {
    throw new IllegalStateException("Utility class");
  }

}
