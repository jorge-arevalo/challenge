package com.globank.management.challenge.infrastructure.config.exception;

import com.globank.management.challenge.infrastructure.rest.output.FormatOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Generic class to handle exceptions to services.
 *
 * @author jorge-arevalo
 */
@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<FormatOutput<Object>> handleIllegalArgumentException(
      IllegalArgumentException exception) {
    log.error(exception.toString());
    return buildResponse(
        String.valueOf(HttpStatus.BAD_REQUEST.value()),
        GeneralExceptionMessages.INTERNAL_FAIL_LOADING_DATA,
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public final ResponseEntity<FormatOutput<Object>> handleErrorException(
      Exception exception) {
    log.error(exception.toString());
    return buildResponse(
        exception.getClass().getName(),
        exception.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<FormatOutput<Object>> buildResponse(
      String code,
      String message,
      HttpStatus httpStatus) {
    FormatOutput<Object> output = new FormatOutput<>();
    output.setCode(code);
    output.setMessage(message);
    return ResponseEntity.status(httpStatus).body(output);
  }

}
