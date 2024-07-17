package me.ryzeon.bankingsystem.account.domain.model.exception;

public class AccountAlreadyExistsException extends RuntimeException{

    public AccountAlreadyExistsException() {
        super("Account number provided already exists.");
    }
}
