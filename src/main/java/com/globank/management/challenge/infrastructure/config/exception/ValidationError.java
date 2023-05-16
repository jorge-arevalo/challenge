package com.globank.management.challenge.infrastructure.config.exception;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Response format for validation errors.
 *
 * @author jorge-arevalo
 */
@Getter
@Setter
@Builder
public class ValidationError {

  private String dtoName;
  private List<ValidationFailDetail> validationFailDetailList;

}
