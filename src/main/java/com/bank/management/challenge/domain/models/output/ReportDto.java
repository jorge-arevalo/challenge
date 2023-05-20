package com.bank.management.challenge.domain.models.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

/**
 * Output DTO for reports.
 *
 * @author jorge-arevalo
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDto {

  private String movementDate;
  private String customerName;
  private String accountNumber;
  private String accountType;
  private BigDecimal initialBalance;
  private Boolean status;
  private BigDecimal movementValue;
  private BigDecimal balance;

}
