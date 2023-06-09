package com.bank.management.challenge.infrastructure.config.exception;

/**
 * Constants to manage the exception messages for the app.
 *
 * @author jorge-arevalo
 */
public class GeneralExceptionMessages {

  // Validation error messages
  public static final String INTERNAL_FAIL_LOADING_DATA =
      "Se ha presentado un error al validar los argumentos de la petición.";
  public static final String METHOD_ARGUMENT_NOT_VALID_MESSAGE =
      "Ocurrió un error en la validación de datos de entrada.";

  // Customer error messages
  public static final String CUSTOMER_NAME_EMPTY =
      "El nombre del cliente no puede estar vacío.";
  public static final String CUSTOMER_NAME_SIZE =
      "El nombre del cliente debe tener entre 2 y 100 caracteres.";
  public static final String CUSTOMER_GENDER_EMPTY =
      "El género del cliente no puede estar vacío.";
  public static final String CUSTOMER_GENDER_SIZE =
      "El género del cliente debe tener entre 2 y 10 caracteres.";
  public static final String CUSTOMER_IDENTIFICATION_EMPTY =
      "La identificación del cliente no puede estar vacía.";
  public static final String CUSTOMER_IDENTIFICATION_SIZE =
      "La identificación del cliente debe tener entre 2 y 20 caracteres.";
  public static final String CUSTOMER_ADDRESS_EMPTY =
      "La dirección del cliente no puede estar vacía.";
  public static final String CUSTOMER_ADDRESS_SIZE =
      "La dirección del cliente debe tener entre 10 y 100 caracteres.";
  public static final String CUSTOMER_PHONE_NUMBER_EMPTY =
      "El teléfono del cliente no puede estar vacío.";
  public static final String CUSTOMER_PHONE_NUMBER_SIZE =
      "El teléfono del cliente debe tener entre 5 y 20 caracteres.";
  public static final String CUSTOMER_KEY_EMPTY =
      "La contraseña del cliente no puede estar vacía.";
  public static final String CUSTOMER_KEY_SIZE =
      "\"La contraseña del cliente debe tener entre 4 y 50 caracteres";
  public static final String CUSTOMER_NOT_FOUND =
      "El cliente no existe en la base de datos.";
  public static final String CUSTOMERS_NOT_FOUND =
      "No hay clientes registrados en la base de datos.";
  public static final String INVALID_CUSTOMER_ID =
      "El identificador del cliente no es válido.";

  // Account error messages
  public static final String ACCOUNT_NUMBER_EMPTY =
      "El número de cuenta no puede estar vacío.";
  public static final String ACCOUNT_NUMBER_SIZE =
      "El número de cuenta debe tener entre 5 y 20 caracteres.";
  public static final String ACCOUNT_TYPE_EMPTY =
      "El tipo de cuenta no puede estar vacío.";
  public static final String ACCOUNT_TYPE_SIZE =
      "El tipo de cuenta debe tener entre 3 y 20 caracteres.";
  public static final String ACCOUNT_INITIAL_BALANCE_EMPTY =
      "El saldo inicial de la cuenta no puede estar vacío.";
  public static final String ACCOUNT_INITIAL_BALANCE_MINIMUM =
      "El saldo inicial de la cuenta debe ser mayor o igual a 0.";
  public static final String ACCOUNT_INITIAL_BALANCE_MAXIMUM_VALUES =
      "El saldo inicial excede los límites autorizados.";
  public static final String ACCOUNT_CUSTOMER_NAME_EMPTY =
      "El nombre del cliente no puede estar vacío.";
  public static final String ACCOUNT_CUSTOMER_NAME_SIZE =
      "El nombre del cliente debe tener entre 2 y 100 caracteres.";
  public static final String ACCOUNT_NOT_FOUND =
      "La cuenta no existe en la base de datos.";
  public static final String ACCOUNTS_NOT_FOUND =
      "No hay cuentas registradas en la base de datos.";
  public static final String INVALID_ACCOUNT_ID =
      "El identificador de la cuenta no es válido.";

  // Movement error messages
  public static final String MOVEMENT_TYPE_EMPTY =
      "El tipo de movimiento no puede estar vacío.";
  public static final String MOVEMENT_TYPE_SIZE =
      "El tipo de movimiento debe tener entre 3 y 20 caracteres.";
  public static final String MOVEMENT_VALUE_EMPTY =
      "El monto del movimiento no puede estar vacío.";
  public static final String MOVEMENT_VALUE_MINIMUM =
      "El monto del movimiento debe ser mayor o igual a 0.";
  public static final String MOVEMENT_VALUE_MAXIMUM_VALUES =
      "El monto del movimiento excede los límites autorizados.";
  public static final String MOVEMENT_ACCOUNT_NUMBER_EMPTY =
      "El número de cuenta no puede estar vacío.";
  public static final String MOVEMENT_ACCOUNT_NUMBER_SIZE =
      "El número de cuenta debe tener entre 5 y 20 caracteres.";
  public static final String MOVEMENT_NOT_FOUND =
      "El movimiento no existe en la base de datos.";
  public static final String MOVEMENTS_NOT_FOUND =
      "No hay movimientos registrados en la base de datos.";
  public static final String INSUFFICIENT_BALANCE =
      "El saldo de la cuenta no es suficiente para realizar el movimiento.";
  public static final String INVALID_MOVEMENT_ID =
      "El identificador del movimiento no es válido.";

  private GeneralExceptionMessages() {
    // Default constructor
  }

}
