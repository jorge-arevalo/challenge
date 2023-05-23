package com.bank.management.challenge.domain.models.input;

import com.bank.management.challenge.infrastructure.config.exception.GeneralExceptionMessages;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

/**
 * Input DTO for the customer entity.
 *
 * @author jorge-arevalo
 */
@Getter
@Builder
public class CustomerInputDto {

  @NotEmpty(message = GeneralExceptionMessages.CUSTOMER_NAME_EMPTY)
  @Size(min = 2, max = 100, message = GeneralExceptionMessages.CUSTOMER_NAME_SIZE)
  private String name;

  @NotEmpty(message = GeneralExceptionMessages.CUSTOMER_GENDER_EMPTY)
  @Size(min = 2, max = 10, message = GeneralExceptionMessages.CUSTOMER_GENDER_SIZE)
  private String gender;

  private Integer age;

  @NotEmpty(message = GeneralExceptionMessages.CUSTOMER_IDENTIFICATION_EMPTY)
  @Size(min = 2, max = 20, message = GeneralExceptionMessages.CUSTOMER_IDENTIFICATION_SIZE)
  private String identification;

  @NotEmpty(message = GeneralExceptionMessages.CUSTOMER_ADDRESS_EMPTY)
  @Size(min = 10, max = 100, message = GeneralExceptionMessages.CUSTOMER_ADDRESS_SIZE)
  private String address;

  @NotEmpty(message = GeneralExceptionMessages.CUSTOMER_PHONE_NUMBER_EMPTY)
  @Size(min = 5, max = 20, message = GeneralExceptionMessages.CUSTOMER_PHONE_NUMBER_SIZE)
  private String phoneNumber;

  @NotEmpty(message = GeneralExceptionMessages.CUSTOMER_KEY_EMPTY)
  @Size(min = 4, max = 50, message = GeneralExceptionMessages.CUSTOMER_KEY_SIZE)
  private String password;

  private Boolean status;

}
