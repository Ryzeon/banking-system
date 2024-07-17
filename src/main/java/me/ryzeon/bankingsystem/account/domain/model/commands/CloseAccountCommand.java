package me.ryzeon.bankingsystem.account.domain.model.commands;

public record CloseAccountCommand(
        String accountNumber
) {
}
