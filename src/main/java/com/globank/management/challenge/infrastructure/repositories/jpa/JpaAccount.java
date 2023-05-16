package com.globank.management.challenge.infrastructure.repositories.jpa;

import com.globank.management.challenge.infrastructure.entities.Account;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Code description
 *
 * @author jorge-arevalo
 */
public interface JpaAccount extends JpaRepository<Account, UUID> {

}
