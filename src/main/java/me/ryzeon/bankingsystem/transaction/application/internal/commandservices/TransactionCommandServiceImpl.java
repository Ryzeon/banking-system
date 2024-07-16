package me.ryzeon.bankingsystem.transaction.application.internal.commandservices;

import lombok.AllArgsConstructor;
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
        if (externalAccountService.existsAccountHolderWithAccountNumber(command.accountNumber())) {
            Double accountBalance = externalAccountService.getAccountBalance(command.accountNumber());

            // Check if the transaction type is valid or not
            TransactionType transactionType;
            try {
                transactionType = TransactionType.valueOf(command.transactionType());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid transaction type provided.");
            }

            // Check if the account balance is sufficient for the transaction
            // If the transaction type is DEPOSIT, then the transaction is allowed
            if (accountBalance >= command.amount() || transactionType == TransactionType.DEPOSIT) {
                Double newBalance = transactionType == TransactionType.DEPOSIT ? accountBalance + command.amount() : accountBalance - command.amount();
                externalAccountService.updateAccountBalance(command.accountNumber(), newBalance);
                return Optional.of(transactionRepository.save(new Transaction(command, accountBalance, newBalance)));
            } else {
                throw new RuntimeException("Insufficient balance in the account.");
            }
        } else {
            throw new RuntimeException("Account number provided does not exist.");
        }
    }
}
