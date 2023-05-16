package com.globank.management.challenge.infrastructure.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Class for representing a customer.
 *
 * @author jorge-arevalo
 */
@Entity(name = "customer")
@DiscriminatorValue("customer")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Person {

  @Column(name = "password", nullable = false, length = 50)
  private String password;

  @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
  private Boolean status;

}
