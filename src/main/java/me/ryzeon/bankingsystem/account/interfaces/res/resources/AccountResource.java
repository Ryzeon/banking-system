package me.ryzeon.bankingsystem.account.interfaces.res.resources;

public record AccountResource (
        String id,
        Double balance,
        String names,
        String lastNames,
        String accountNumber
){
}
