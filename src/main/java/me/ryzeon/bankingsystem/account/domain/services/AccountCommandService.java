package me.ryzeon.bankingsystem.account.domain.services;

import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.CloseAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountBalanceCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountDetailsCommand;

import java.util.Optional;

public interface AccountCommandService {

    Optional<Account> handle(CreateAccountCommand command);

    void handle(UpdateAccountBalanceCommand command);

    void handle(CloseAccountCommand command);

    void handle(UpdateAccountDetailsCommand command);
}
