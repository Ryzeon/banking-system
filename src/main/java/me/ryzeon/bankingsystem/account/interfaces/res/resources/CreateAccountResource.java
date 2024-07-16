package me.ryzeon.bankingsystem.account.interfaces.res.resources;

public record CreateAccountResource(
        String accountNumber,
        Double initialBalance,
        String names,
        String lastNames,
        String email,
        String documentNumber
){
}
