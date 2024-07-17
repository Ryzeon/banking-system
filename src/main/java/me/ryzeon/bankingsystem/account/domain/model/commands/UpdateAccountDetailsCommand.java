package me.ryzeon.bankingsystem.account.domain.model.commands;

public record UpdateAccountDetailsCommand(
        String accountNumber,
        String names,
        String lastNames,
        String email,
        String documentNumber
) {
}
