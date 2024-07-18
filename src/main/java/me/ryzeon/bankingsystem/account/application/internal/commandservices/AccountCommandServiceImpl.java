package me.ryzeon.bankingsystem.account.application.internal.commandservices;

import lombok.AllArgsConstructor;
import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.CloseAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountBalanceCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountDetailsCommand;
import me.ryzeon.bankingsystem.account.domain.model.exception.AccountAlreadyClosedException;
import me.ryzeon.bankingsystem.account.domain.model.exception.AccountAlreadyExistsException;
import me.ryzeon.bankingsystem.account.domain.model.exception.AccountClosedException;
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
     * Handles the creation of a new account. If an account with the given account number already exists,
     * it throws an AccountAlreadyExistsException. Otherwise, it saves the new account to the repository.
     *
     * @param command The command containing the details for creating a new account.
     * @return An Optional containing the created Account, or an empty Optional if the account already exists.
     * @throws AccountAlreadyExistsException if an account with the specified account number already exists.
     */
    @Override
    public Optional<Account> handle(CreateAccountCommand command) {
        if (accountRepository.findByAccountNumber(command.accountNumber()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }
        return Optional.of(accountRepository.save(new Account(command)));
    }

    /**
     * Updates the balance of an existing account. If the account does not exist or is closed,
     * it throws an AccountNotFoundException or AccountClosedException respectively.
     *
     * @param command The command containing the account number and the new balance amount.
     * @throws AccountNotFoundException if the account with the specified number does not exist.
     * @throws AccountClosedException   if the account is closed.
     */
    @Override
    public void handle(UpdateAccountBalanceCommand command) {
        Account account = accountRepository.findByAccountNumber(command.accountNumber())
                .orElseThrow(AccountNotFoundException::new);
        if (!account.isActiveAccount()) {
            throw new AccountClosedException();
        }
        account.setBalance(command.amount());
        accountRepository.save(account);
    }

    /**
     * Closes an existing account. If the account does not exist or is already closed,
     * it throws an AccountNotFoundException or AccountAlreadyClosedException respectively.
     *
     * @param command The command containing the account number of the account to be closed.
     * @throws AccountNotFoundException      if the account with the specified number does not exist.
     * @throws AccountAlreadyClosedException if the account is already closed.
     */
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

    /**
     * Updates the details of an existing account. If the account does not exist,
     * it throws an AccountNotFoundException.
     *
     * @param command The command containing the account number and new details to update.
     * @throws AccountNotFoundException if the account with the specified number does not exist.
     */
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