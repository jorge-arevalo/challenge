package com.globank.management.challenge.infrastructure.entities;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 * Class for representing an account.
 *
 * @author jorge-arevalo
 */
@Entity(name = "account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false, columnDefinition = "uuid")
  @Type(type = "org.hibernate.type.PostgresUUIDType")
  private UUID id;

  @Column(name = "account_number", nullable = false, length = 20)
  private String accountNumber;

  @Column(name = "account_type", nullable = false, length = 20)
  private String accountType;

  @Column(name = "initial_balance", nullable = false)
  private BigDecimal initialBalance;

  @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
  private Boolean status;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

}
