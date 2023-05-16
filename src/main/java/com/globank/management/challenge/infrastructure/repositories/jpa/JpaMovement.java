package com.globank.management.challenge.infrastructure.repositories.jpa;

import com.globank.management.challenge.infrastructure.entities.Movement;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Code description
 *
 * @author jorge-arevalo
 */
public interface JpaMovement extends JpaRepository<Movement, UUID> {

}
