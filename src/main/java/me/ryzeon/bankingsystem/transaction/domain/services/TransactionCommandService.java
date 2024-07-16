package me.ryzeon.bankingsystem.transaction.domain.services;

import me.ryzeon.bankingsystem.transaction.domain.model.aggregates.Transaction;
import me.ryzeon.bankingsystem.transaction.domain.model.commands.CreateTransactionCommand;

import java.util.Optional;

public interface TransactionCommandService {

    Optional<Transaction> handle(CreateTransactionCommand command);
}
