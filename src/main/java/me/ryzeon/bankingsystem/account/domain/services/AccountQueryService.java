package me.ryzeon.bankingsystem.account.domain.services;

import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountByIdQuery;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountHolderByAccountNumberQuery;

import java.util.Optional;

public interface AccountQueryService {

    Optional<Account> handle(GetAccountByIdQuery query);

    Optional<Account> handle(GetAccountHolderByAccountNumberQuery query);
}
