package com.globank.management.challenge.infrastructure.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime movementDate;

  @Column(name = "movement_type", nullable = false, length = 20)
  private String movementType;

  @Column(name = "value", nullable = false, columnDefinition = "double default 0.0")
  private BigDecimal value;

  @Column(name = "balance", nullable = false, columnDefinition = "double default 0.0")
  private BigDecimal balance;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

}
