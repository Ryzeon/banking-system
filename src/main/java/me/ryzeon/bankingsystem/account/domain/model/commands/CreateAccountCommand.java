package me.ryzeon.bankingsystem.account.domain.model.commands;

public record CreateAccountCommand(
        String accountNumber,
        Double initialBalance,
        String names,
        String lastNames,
        String email,
        String documentNumber
) {
}