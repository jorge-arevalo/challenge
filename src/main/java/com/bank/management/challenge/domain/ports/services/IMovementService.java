package com.bank.management.challenge.domain.ports.services;

import com.bank.management.challenge.domain.models.input.MovementInputDto;
import com.bank.management.challenge.domain.models.output.MovementDto;
import java.util.List;

/**
 * Service interface for the movement entity.
 *
 * @author jorge-arevalo
 */
public interface IMovementService {

  MovementDto save(MovementInputDto movementInput);

  MovementDto findById(String id);

  List<MovementDto> findAll();

  MovementDto update(String id, MovementInputDto movementInput);

  void delete(String id);

}
