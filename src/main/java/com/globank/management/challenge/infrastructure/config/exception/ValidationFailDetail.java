package com.globank.management.challenge.infrastructure.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Validation fail detail.
 *
 * @author jorge-arevalo
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationFailDetail {

  private String code;
  private String detail;

}
