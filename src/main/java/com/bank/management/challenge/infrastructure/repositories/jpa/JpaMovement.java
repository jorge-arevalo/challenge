package com.bank.management.challenge.infrastructure.repositories.jpa;

import com.bank.management.challenge.infrastructure.entities.Movement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Code description
 *
 * @author jorge-arevalo
 */
public interface JpaMovement extends JpaRepository<Movement, UUID> {

  @Query(value = "SELECT m.* FROM challenge.movement m "
      + "INNER JOIN challenge.account a ON m.account_id = a.id "
      + "INNER JOIN challenge.customer c ON a.customer_id = c.id "
      + "INNER JOIN challenge.person p ON c.id = p.id "
      + "WHERE m.movement_date BETWEEN :initialDate AND :finalDate "
      + "AND p.name ILIKE :customerName "
      + "ORDER BY m.movement_date DESC ", nativeQuery = true)
  List<Movement> findByMovementDateBetween(@Param("initialDate") LocalDateTime initialDate,
      @Param("finalDate") LocalDateTime finalDate, @Param("customerName") String customerName);

}
