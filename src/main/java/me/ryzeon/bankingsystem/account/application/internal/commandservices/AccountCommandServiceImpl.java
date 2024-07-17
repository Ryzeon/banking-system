package me.ryzeon.bankingsystem.account.application.internal.commandservices;

import lombok.AllArgsConstructor;
import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.CloseAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountBalanceCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountDetailsCommand;
import me.ryzeon.bankingsystem.account.domain.model.exception.AccountAlreadyClosedException;
import me.ryzeon.bankingsystem.account.domain.model.exception.AccountAlreadyExistsException;
import me.ryzeon.bankingsystem.account.domain.model.exception.AccountNotFoundException;
import me.ryzeon.bankingsystem.account.domain.model.valueobjects.AccountInformation;
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
     *
     * @param command CreateAccountCommand see {@link CreateAccountCommand}
     * @return Optional<Account> see {@link Account}
     */
    @Override
    public Optional<Account> handle(CreateAccountCommand command) {
        if (accountRepository.findByAccountNumber(command.accountNumber()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }
        return Optional.of(accountRepository.save(new Account(command)));
    }

    @Override
    public void handle(UpdateAccountBalanceCommand command) {
        Account account = accountRepository.findByAccountNumber(command.accountNumber())
                .orElseThrow(AccountNotFoundException::new);
        account.setBalance(command.amount());
        accountRepository.save(account);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        Account account = accountRepository.findByAccountNumber(command.accountNumber())
                .orElseThrow(AccountNotFoundException::new);
        if (account.isActiveAccount()) {
            account.closeAccount();
            accountRepository.save(account);
        } else {
            throw new AccountAlreadyClosedException();
        }
    }

    @Override
    public void handle(UpdateAccountDetailsCommand command) {
        Account account = accountRepository.findByAccountNumber(command.accountNumber())
                .orElseThrow(AccountNotFoundException::new);

        account.updateInformation(
                new AccountInformation(
                        command.names(),
                        command.lastNames(),
                        command.email(),
                        command.documentNumber()
                )
        );
        accountRepository.save(account);
    }
}
