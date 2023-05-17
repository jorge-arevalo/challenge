package com.bank.management.challenge.infrastructure.config.exception;

import com.bank.management.challenge.infrastructure.rest.output.FormatOutput;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
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

  @ExceptionHandler(CustomerNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public final ResponseEntity<FormatOutput<Object>> handleCustomerNotFoundException(
      CustomerNotFoundException exception) {
    log.error(exception.getMessage(), exception);
    return buildResponse(
        String.valueOf(HttpStatus.NOT_FOUND.value()),
        exception.getMessage(),
        HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      @NotNull MethodArgumentNotValidException ex, @Nullable HttpHeaders headers,
      @Nullable HttpStatus status, @Nullable WebRequest request) {
    List<ValidationFailDetail> validationFailDetails = ex.getBindingResult().getFieldErrors()
        .stream().map(x -> new ValidationFailDetail(x.getField(), x.getDefaultMessage())).collect(
            Collectors.toList());
    String dtoClassName = getClassName(ex.getParameter().getGenericParameterType().getTypeName());
    var validationError = new ValidationError(dtoClassName, validationFailDetails);
    FormatOutput<ValidationError> output = new FormatOutput<>();
    output.setData(validationError);
    output.setCode(getClassName(ex.getClass().getTypeName()));
    output.setMessage(GeneralExceptionMessages.METHOD_ARGUMENT_NOT_VALID_MESSAGE);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(output);
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

  private String getClassName(String fullClassName) {
    String dtoClasssName = StringUtils.substringAfterLast(fullClassName, ".");
    dtoClasssName = StringUtils.substringBefore(dtoClasssName, ">");
    return dtoClasssName;
  }

}
