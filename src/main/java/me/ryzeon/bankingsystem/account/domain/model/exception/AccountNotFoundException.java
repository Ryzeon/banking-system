package me.ryzeon.bankingsystem.account.domain.model.exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException() {
        super("Account number provided does not exist.");
    }
}
