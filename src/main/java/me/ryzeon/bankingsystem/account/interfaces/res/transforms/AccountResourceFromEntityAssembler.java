package me.ryzeon.bankingsystem.account.interfaces.res.transforms;

import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.interfaces.res.resources.AccountResource;

public class AccountResourceFromEntityAssembler {

    private AccountResourceFromEntityAssembler() {
    }

    public static AccountResource toResourceFromEntity(Account entity) {
        return new AccountResource(
                entity.getId(),
                entity.getBalance(),
                entity.getInformation().names(),
                entity.getInformation().lastNames(),
                entity.getAccountNumber()
        );
    }
}
