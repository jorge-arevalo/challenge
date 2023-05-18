package com.bank.management.challenge.domain.models.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Output DTO for the account entity.
 *
 * @author jorge-arevalo
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

  private String id;
  private String accountNumber;
  private String accountType;
  private BigDecimal initialBalance;
  private Boolean status;
  private CustomerDto customer;

}
