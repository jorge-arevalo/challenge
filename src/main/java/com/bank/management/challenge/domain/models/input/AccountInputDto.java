package com.bank.management.challenge.domain.models.input;

import com.bank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Input DTO for the account.
 *
 * @author jorge-arevalo
 */
@Getter
@Setter
public class AccountInputDto {

  @NotEmpty(message = GeneralExceptionMessages.ACCOUNT_NUMBER_EMPTY)
  @Size(min = 5, max = 20, message = GeneralExceptionMessages.ACCOUNT_NUMBER_SIZE)
  private String accountNumber;

  @NotEmpty(message = GeneralExceptionMessages.ACCOUNT_TYPE_EMPTY)
  @Size(min = 3, max = 20, message = GeneralExceptionMessages.ACCOUNT_TYPE_SIZE)
  private String accountType;

  @NotNull(message = GeneralExceptionMessages.ACCOUNT_INITIAL_BALANCE_EMPTY)
  private BigDecimal initialBalance;

  private Boolean status;

  @NotEmpty(message = GeneralExceptionMessages.ACCOUNT_CUSTOMER_NAME_EMPTY)
  @Size(min = 2, max = 100, message = GeneralExceptionMessages.ACCOUNT_CUSTOMER_NAME_SIZE)
  private String customerName;

}
