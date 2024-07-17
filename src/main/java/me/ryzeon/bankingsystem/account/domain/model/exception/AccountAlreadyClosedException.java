package me.ryzeon.bankingsystem.account.domain.model.exception;

public class AccountAlreadyClosedException extends RuntimeException{

    public AccountAlreadyClosedException() {
        super("Account is already closed.");
    }
}
