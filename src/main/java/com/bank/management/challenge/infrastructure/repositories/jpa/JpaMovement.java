package com.bank.management.challenge.infrastructure.repositories.jpa;

import com.bank.management.challenge.infrastructure.entities.Movement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Code description
 *
 * @author jorge-arevalo
 */
public interface JpaMovement extends JpaRepository<Movement, UUID> {

  List<Movement> findByMovementDateBetween(LocalDateTime initialDate, LocalDateTime finalDate);

}
