package me.ryzeon.bankingsystem.account.application.internal.queryservices;

import lombok.AllArgsConstructor;
import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountByIdQuery;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountHolderByAccountNumberQuery;
import me.ryzeon.bankingsystem.account.domain.services.AccountQueryService;
import me.ryzeon.bankingsystem.account.infrastructure.persistence.jpa.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountQueryServiceImpl implements AccountQueryService {

    private final AccountRepository accountRepository;

    /**
     * Get Account by id
     * @param query GetAccountByIdQuery see {@link GetAccountByIdQuery}
     * @return Optional<Account> see {@link Account}
     */
    @Override
    public Optional<Account> handle(GetAccountByIdQuery query) {
        return accountRepository.findById(query.id());
    }

    /**
     * Get Account by account number {@link Long} accountNumber
     * @param query GetAccountByAccountNumberQuery see {@link GetAccountHolderByAccountNumberQuery}
     * @return Optional<Account> see {@link Account}
     */
    @Override
    public Optional<Account> handle(GetAccountHolderByAccountNumberQuery query) {
        return accountRepository.findByAccountNumber(query.accountNumber());
    }
}
