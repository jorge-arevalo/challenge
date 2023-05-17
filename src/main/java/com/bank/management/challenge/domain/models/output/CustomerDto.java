package com.bank.management.challenge.domain.models.output;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Output DTO for the customer entity.
 *
 * @author jorge-arevalo
 */
@Getter
@Setter
@Builder
public class CustomerDto {

  private UUID id;
  private String name;
  private String gender;
  private Integer age;
  private String identification;
  private String address;
  private String phoneNumber;
  private Boolean status;

}
