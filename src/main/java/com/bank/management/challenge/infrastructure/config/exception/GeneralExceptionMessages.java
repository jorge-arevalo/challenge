package com.bank.management.challenge.infrastructure.config.exception;

/**
 * Constants to manage the exception messages for the app.
 *
 * @author jorge-arevalo
 */
public class GeneralExceptionMessages {

  public static final String INTERNAL_FAIL_LOADING_DATA =
      "Se ha presentado un error al validar los argumentos de la petición.";
  public static final String CUSTOMER_NAME_EMPTY = "El nombre del cliente no puede estar vacío.";
  public static final String CUSTOMER_NAME_SIZE =
      "El nombre del cliente debe tener entre 2 y 100 caracteres.";
  public static final String CUSTOMER_GENDER_EMPTY = "El género del cliente no puede estar vacío.";
  public static final String CUSTOMER_GENDER_SIZE =
      "El género del cliente debe tener entre 2 y 10 caracteres.";
  public static final String CUSTOMER_AGE_EMPTY = "La edad del cliente no puede estar vacía.";
  public static final String CUSTOMER_AGE_SIZE =
      "La edad del cliente debe tener entre 2 y 3 caracteres.";
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
  public static final String CUSTOMER_PASSWORD_EMPTY =
      "La contraseña del cliente no puede estar vacía.";
  public static final String CUSTOMER_NOT_FOUND = "El cliente no existe en la base de datos.";
  public static final String CUSTOMERS_NOT_FOUND = "No hay clientes registrados en la base de datos.";
  public static final String METHOD_ARGUMENT_NOT_VALID_MESSAGE =
      "Ocurrió un error en la validación de datos de entrada.";

  private GeneralExceptionMessages() {
    // Default constructor
  }

}
