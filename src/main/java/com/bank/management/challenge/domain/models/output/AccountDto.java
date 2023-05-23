package com.bank.management.challenge.domain.models.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Output DTO for the account entity.
 *
 * @author jorge-arevalo
 */
@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

  private String id;
  private String accountNumber;
  private String accountType;
  private BigDecimal initialBalance;
  private Boolean status;
  private CustomerDto customer;

}
