package com.globank.management.challenge.infrastructure.rest.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Generic class to handle responses to services.
 *
 * @author jorge-arevalo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormatOutput<T> {

  private T data;
  private String code;
  private String message;

}
