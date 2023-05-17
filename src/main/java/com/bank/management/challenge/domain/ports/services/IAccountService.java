package com.bank.management.challenge.domain.ports.services;

import com.bank.management.challenge.domain.models.input.AccountInputDto;
import com.bank.management.challenge.domain.models.output.AccountDto;
import java.util.List;

/**
 * Service interface for the account entity.
 *
 * @author jorge-arevalo
 */
public interface IAccountService {

  AccountDto save(AccountInputDto accountInput);

  AccountDto findById(String id);

  List<AccountDto> findAll();

  AccountDto update(String id, AccountInputDto accountInput);

  void delete(String id);

}
