package com.bank.management.challenge.domain.models.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Output DTO for the customer entity.
 *
 * @author jorge-arevalo
 */
@Getter
@Builder
@ToString
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
