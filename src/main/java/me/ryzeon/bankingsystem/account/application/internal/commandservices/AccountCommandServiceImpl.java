package me.ryzeon.bankingsystem.account.application.internal.commandservices;

import lombok.AllArgsConstructor;
import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountBalanceCommand;
import me.ryzeon.bankingsystem.account.domain.services.AccountCommandService;
import me.ryzeon.bankingsystem.account.infrastructure.persistence.jpa.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountCommandServiceImpl implements AccountCommandService {

    private final AccountRepository accountRepository;

    /**
     * Create Account, if account already exists return empty
     * @param command CreateAccountCommand see {@link CreateAccountCommand}
     * @return Optional<Account> see {@link Account}
     */
    @Override
    public Optional<Account> handle(CreateAccountCommand command) {
        if (accountRepository.findByAccountNumber(command.accountNumber()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(accountRepository.save(new Account(command)));
    }

    @Override
    public void handle(UpdateAccountBalanceCommand command) {
        Account account = accountRepository.findByAccountNumber(command.accountNumber())
                .orElseThrow(() -> new RuntimeException("Account number provided does not exist."));
        account.setBalance(command.amount());
        accountRepository.save(account);
    }
}
