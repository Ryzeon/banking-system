package me.ryzeon.bankingsystem.account.interfaces.res.transforms;

import lombok.experimental.UtilityClass;
import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.interfaces.res.resources.AccountResource;

@UtilityClass
public class AccountResourceFromEntityAssembler {

    public AccountResource toResourceFromEntity(Account entity) {
        return new AccountResource(
                entity.getId(),
                entity.getBalance(),
                entity.getInformation().names(),
                entity.getInformation().lastNames(),
                entity.getAccountNumber()
        );
    }
}
