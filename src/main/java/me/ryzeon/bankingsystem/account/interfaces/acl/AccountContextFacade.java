package me.ryzeon.bankingsystem.account.interfaces.acl;

import lombok.AllArgsConstructor;
import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountBalanceCommand;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountHolderByAccountNumberQuery;
import me.ryzeon.bankingsystem.account.domain.services.AccountCommandService;
import me.ryzeon.bankingsystem.account.domain.services.AccountQueryService;
import org.springframework.stereotype.Service;

@Service
/*
 * This is to simulate an Anti-Corruption Layer (ACL) that would be used to communicate with external systems.
 * In this case, it is used to communicate with the AccountContextFacade.
 */
@AllArgsConstructor
public class AccountContextFacade {

    private final AccountQueryService accountQueryService;
    private final AccountCommandService accountCommandService;

    public boolean existsAccountHolderWithAccountNumber(String accountNumber) {
        return accountQueryService.handle(new GetAccountHolderByAccountNumberQuery(accountNumber)).isPresent();
    }

    public Double getAccountBalance(String accountNumber) {
        return accountQueryService.handle(new GetAccountHolderByAccountNumberQuery(accountNumber))
                .map(Account::getBalance)
                .orElseThrow(() -> new RuntimeException("Account number provided does not exist."));
    }

    public boolean updateAccountBalance(String accountNumber, Double amount) {
        accountCommandService.handle(new UpdateAccountBalanceCommand(accountNumber, amount));
        return true;
    }
}
