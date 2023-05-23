package com.bank.management.challenge.infrastructure.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
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
 * Class for representing a banking movement.
 *
 * @author jorge-arevalo
 */
@Entity(name = "movement")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false, columnDefinition = "uuid")
  @Type(type = "org.hibernate.type.PostgresUUIDType")
  private UUID id;

  @Column(name = "movement_date", nullable = false)
  private LocalDateTime movementDate;

  @Column(name = "movement_type", nullable = false, length = 20)
  private String movementType;

  @Column(name = "movement_value", nullable = false)
  private BigDecimal movementValue;

  @Column(name = "balance", nullable = false)
  private BigDecimal balance;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

}
