package me.ryzeon.bankingsystem.transaction.domain.services;

import me.ryzeon.bankingsystem.transaction.domain.model.aggregates.Transaction;
import me.ryzeon.bankingsystem.transaction.domain.model.commands.CreateTransactionCommand;

import java.util.Optional;

public interface TransactionCommandService {

    /**
     * Handles the creation of a transaction based on the provided command.
     * This method processes the transaction command and returns an Optional containing the created Transaction,
     * or an empty Optional if the transaction could not be processed successfully.
     *
     * @param command The command containing the details for creating a transaction.
     * @return An Optional containing the created Transaction if successful, or an empty Optional otherwise.
     */
    Optional<Transaction> handle(CreateTransactionCommand command);
}