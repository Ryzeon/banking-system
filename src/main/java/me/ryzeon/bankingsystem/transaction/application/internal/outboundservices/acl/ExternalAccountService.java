package me.ryzeon.bankingsystem.transaction.application.internal.outboundservices.acl;

import lombok.AllArgsConstructor;
import me.ryzeon.bankingsystem.account.interfaces.acl.AccountContextFacade;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExternalAccountService {

    private final AccountContextFacade accountContextFacade;

    /**
     * Check if an account holder exists with the given account number
     * @param accountNumber {@link String} accountNumber
     * @return boolean
     */
    public boolean existsAccountHolderWithAccountNumber(String accountNumber) {
        return accountContextFacade.existsAccountHolderWithAccountNumber(accountNumber);
    }

    /**
     * Get the account balance of the given account number
     * @param accountNumber {@link String} accountNumber
     * @return {@link Double} account balance
     */
    public Double getAccountBalance(String accountNumber) {
        return accountContextFacade.getAccountBalance(accountNumber);
    }

    /**
     * Update the account balance of the given account number
     * @param accountNumber {@link String} accountNumber
     * @param amount {@link Double} amount
     */
    public void updateAccountBalance(String accountNumber, Double amount) {
        accountContextFacade.updateAccountBalance(accountNumber, amount);
    }
}
