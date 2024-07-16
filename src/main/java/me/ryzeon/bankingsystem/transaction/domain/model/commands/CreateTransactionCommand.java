package me.ryzeon.bankingsystem.transaction.domain.model.commands;

public record CreateTransactionCommand(
        String accountNumber,
        Double amount,
        String transactionType
) {
}
