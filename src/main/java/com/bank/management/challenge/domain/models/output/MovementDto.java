package com.bank.management.challenge.domain.models.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Output DTO for movement entity.
 *
 * @author jorge-arevalo
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementDto {

  private String id;
  private String movementDate;
  private String movementType;
  private BigDecimal movementValue;
  private BigDecimal balance;
  private AccountDto account;

}
