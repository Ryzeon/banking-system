package me.ryzeon.bankingsystem.transaction.application.internal.commandservices;

import lombok.AllArgsConstructor;
import me.ryzeon.bankingsystem.account.domain.model.exception.AccountNotFoundException;
import me.ryzeon.bankingsystem.account.domain.model.exception.InsufficientBalanceException;
import me.ryzeon.bankingsystem.account.domain.model.exception.InvalidTransactionException;
import me.ryzeon.bankingsystem.transaction.application.internal.outboundservices.acl.ExternalAccountService;
import me.ryzeon.bankingsystem.transaction.domain.model.aggregates.Transaction;
import me.ryzeon.bankingsystem.transaction.domain.model.commands.CreateTransactionCommand;
import me.ryzeon.bankingsystem.transaction.domain.model.valueobjects.TransactionType;
import me.ryzeon.bankingsystem.transaction.domain.services.TransactionCommandService;
import me.ryzeon.bankingsystem.transaction.infrastructure.persistence.jpa.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionCommandServiceImpl implements TransactionCommandService {

    private final TransactionRepository transactionRepository;
    private final ExternalAccountService externalAccountService;

    @Override
    public Optional<Transaction> handle(CreateTransactionCommand command) {
        // Check if the account holder exists for the given account number
        if (externalAccountService.existsAccountHolderWithAccountNumber(command.accountNumber())) {
            // Retrieve the current balance of the account
            Double accountBalance = externalAccountService.getAccountBalance(command.accountNumber());

            // Attempt to parse the transaction type from the command
            TransactionType transactionType;
            try {
                transactionType = TransactionType.valueOf(command.transactionType());
            } catch (IllegalArgumentException e) {
                // If the transaction type is invalid, throw an exception
                throw new InvalidTransactionException();
            }

            // Check if the account has sufficient balance for withdrawal transactions
            // For deposit transactions, proceed without checking the balance
            if (accountBalance >= command.amount() || transactionType == TransactionType.DEPOSIT) {
                // Calculate the new balance after the transaction
                Double newBalance = transactionType == TransactionType.DEPOSIT ? accountBalance + command.amount() : accountBalance - command.amount();
                // Update the account balance
                externalAccountService.updateAccountBalance(command.accountNumber(), newBalance);
                // Save the transaction and return it
                return Optional.of(transactionRepository.save(new Transaction(command, accountBalance, newBalance)));
            } else {
                // If the balance is insufficient for withdrawal, throw an exception
                throw new InsufficientBalanceException();
            }
        } else {
            // If the account holder does not exist, throw an exception
            throw new AccountNotFoundException();
        }
    }
}
