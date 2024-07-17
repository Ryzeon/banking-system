package me.ryzeon.bankingsystem.transaction.interfaces.res.resources;

public record TransactionResource(
        String id,
        String accountNumber,
        Double oldBalance,
        Double amount
) {}
