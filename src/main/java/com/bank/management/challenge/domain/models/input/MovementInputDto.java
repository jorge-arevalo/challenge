package com.bank.management.challenge.domain.models.input;

import com.bank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Input DTO for the movement entity.
 *
 * @author jorge-arevalo
 */
@Getter
@Setter
public class MovementInputDto {

  @NotEmpty(message = GeneralExceptionMessages.MOVEMENT_TYPE_EMPTY)
  @Size(min = 3, max = 20, message = GeneralExceptionMessages.MOVEMENT_TYPE_SIZE)
  private String movementType;

  @NotNull(message = GeneralExceptionMessages.MOVEMENT_VALUE_EMPTY)
  @DecimalMin(value = "0.0", message = GeneralExceptionMessages.MOVEMENT_VALUE_MINIMUM)
  @Digits(integer = 10, fraction = 2, message =
      GeneralExceptionMessages.MOVEMENT_VALUE_MAXIMUM_VALUES)
  private BigDecimal movementValue;

  @NotEmpty(message = GeneralExceptionMessages.MOVEMENT_ACCOUNT_NUMBER_EMPTY)
  @Size(min = 5, max = 20, message = GeneralExceptionMessages.MOVEMENT_ACCOUNT_NUMBER_SIZE)
  private String accountNumber;

}
