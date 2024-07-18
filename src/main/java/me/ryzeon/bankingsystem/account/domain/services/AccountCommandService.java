package me.ryzeon.bankingsystem.account.domain.services;

import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.CloseAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountBalanceCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountDetailsCommand;

import java.util.Optional;

/**
 * Interface for account command services.
 * Defines the operations that can be performed on accounts, including creating, updating balance,
 * closing, and updating details of an account.
 */
public interface AccountCommandService {

    /**
     * Handles the creation of a new account.
     *
     * @param command The command containing the details for creating a new account.
     * @return An Optional containing the created Account if successful, or an empty Optional otherwise.
     */
    Optional<Account> handle(CreateAccountCommand command);

    /**
     * Handles updating the balance of an existing account.
     *
     * @param command The command containing the account number and the new balance.
     */
    void handle(UpdateAccountBalanceCommand command);

    /**
     * Handles the closing of an existing account.
     *
     * @param command The command containing the account number of the account to be closed.
     */
    void handle(CloseAccountCommand command);

    /**
     * Handles updating the details of an existing account.
     *
     * @param command The command containing the account number and the new details to be updated.
     */
    void handle(UpdateAccountDetailsCommand command);
}