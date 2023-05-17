package com.bank.management.challenge.infrastructure.rest.input;

import javax.validation.Valid;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * General input DTO for the rest controllers.
 *
 * @author jorge-arevalo
 */
@Getter
@Setter
public class FormatInput<T> {

  @Valid
  @NonNull
  T data;

}
