package me.ryzeon.bankingsystem.account.interfaces.res.resources;

public record UpdateAccountDetailsResource(
        String names,
        String lastNames,
        String email,
        String documentNumber
) {
}
