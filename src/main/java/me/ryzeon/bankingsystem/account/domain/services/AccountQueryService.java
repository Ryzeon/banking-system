package me.ryzeon.bankingsystem.account.domain.services;

import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountByIdQuery;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountHolderByAccountNumberQuery;

import java.util.Optional;

/**
 * Interface for account query services.
 * Provides operations for querying account information, such as retrieving account details by ID or account number.
 */
public interface AccountQueryService {

    /**
     * Handles the retrieval of an account by its ID.
     *
     * @param query The query containing the ID of the account to be retrieved.
     * @return An Optional containing the Account if found, or an empty Optional if not found.
     */
    Optional<Account> handle(GetAccountByIdQuery query);

    /**
     * Handles the retrieval of an account holder by their account number.
     *
     * @param query The query containing the account number of the account holder to be retrieved.
     * @return An Optional containing the Account if the account holder is found, or an empty Optional if not found.
     */
    Optional<Account> handle(GetAccountHolderByAccountNumberQuery query);
}