package me.ryzeon.bankingsystem.account.interfaces.acl;

import lombok.AllArgsConstructor;
import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountBalanceCommand;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountHolderByAccountNumberQuery;
import me.ryzeon.bankingsystem.account.domain.services.AccountCommandService;
import me.ryzeon.bankingsystem.account.domain.services.AccountQueryService;
import org.springframework.stereotype.Service;

/**
 * Facade for interacting with account-related operations.
 * This class acts as an Anti-Corruption Layer (ACL) to communicate with external systems or services
 * related to account information and operations. It provides a simplified interface for account operations
 * such as checking if an account holder exists, retrieving account balance, and updating account balance.
 */
@Service
@AllArgsConstructor
public class AccountContextFacade {

    private final AccountQueryService accountQueryService;
    private final AccountCommandService accountCommandService;

    /**
     * Checks if an account holder exists for a given account number.
     *
     * @param accountNumber The account number to check.
     * @return true if the account holder exists, false otherwise.
     */
    public boolean existsAccountHolderWithAccountNumber(String accountNumber) {
        return accountQueryService.handle(new GetAccountHolderByAccountNumberQuery(accountNumber)).isPresent();
    }

    /**
     * Retrieves the balance of a given account number.
     *
     * @param accountNumber The account number whose balance is to be retrieved.
     * @return The balance of the account.
     * @throws RuntimeException if the account number does not exist.
     */
    public Double getAccountBalance(String accountNumber) {
        return accountQueryService.handle(new GetAccountHolderByAccountNumberQuery(accountNumber))
                .map(Account::getBalance)
                .orElseThrow(() -> new RuntimeException("Account number provided does not exist."));
    }

    /**
     * Updates the balance of a given account number.
     *
     * @param accountNumber The account number whose balance is to be updated.
     * @param amount        The new balance amount.
     * @return true if the update operation is successful, false otherwise.
     */
    public boolean updateAccountBalance(String accountNumber, Double amount) {
        accountCommandService.handle(new UpdateAccountBalanceCommand(accountNumber, amount));
        return true;
    }
}