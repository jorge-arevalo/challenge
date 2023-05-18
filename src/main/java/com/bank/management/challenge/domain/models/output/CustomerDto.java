package com.bank.management.challenge.domain.models.output;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {

  private String id;
  private String name;
  private String gender;
  private Integer age;
  private String identification;
  private String address;
  private String phoneNumber;
  private Boolean status;

}
