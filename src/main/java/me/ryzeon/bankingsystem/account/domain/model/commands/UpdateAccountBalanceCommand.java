package me.ryzeon.bankingsystem.account.domain.model.commands;

public record UpdateAccountBalanceCommand(
        String accountNumber,
        double amount
) {
}
