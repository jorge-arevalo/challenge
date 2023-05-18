package com.bank.management.challenge.domain.ports.repositories;

import com.bank.management.challenge.infrastructure.entities.Movement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for the movement entity.
 *
 * @author jorge-arevalo
 */
public interface IMovementRepository {

  Movement save(Movement movement);

  Optional<Movement> findById(UUID id);

  List<Movement> findAll();

  List<Movement> findByMovementDateBetween(LocalDateTime initialDate, LocalDateTime finalDate);

  void delete(Movement movement);

}
