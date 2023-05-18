package com.bank.management.challenge.infrastructure.repositories;

import com.bank.management.challenge.domain.ports.repositories.IMovementRepository;
import com.bank.management.challenge.infrastructure.entities.Movement;
import com.bank.management.challenge.infrastructure.repositories.jpa.JpaMovement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the repository interface for the Movement entity.
 *
 * @author jorge-arevalo
 */
@Repository
@RequiredArgsConstructor
public class MovementRepository implements IMovementRepository {

  private final JpaMovement jpaMovement;

  @Override
  public Movement save(Movement movement) {
    return jpaMovement.save(movement);
  }

  @Override
  public Optional<Movement> findById(UUID id) {
    return jpaMovement.findById(id);
  }

  @Override
  public List<Movement> findAll() {
    return jpaMovement.findAll();
  }

  @Override
  public List<Movement> findByMovementDateBetween(LocalDateTime initialDate,
      LocalDateTime finalDate) {
    return jpaMovement.findByMovementDateBetween(initialDate, finalDate);
  }

  @Override
  public void delete(Movement movement) {
    jpaMovement.delete(movement);
  }

}
