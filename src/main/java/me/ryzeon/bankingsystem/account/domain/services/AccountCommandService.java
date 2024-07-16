package me.ryzeon.bankingsystem.account.domain.services;

import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountBalanceCommand;

import java.util.Optional;

public interface AccountCommandService {

    Optional<Account> handle(CreateAccountCommand command);

    void handle(UpdateAccountBalanceCommand command);
}
