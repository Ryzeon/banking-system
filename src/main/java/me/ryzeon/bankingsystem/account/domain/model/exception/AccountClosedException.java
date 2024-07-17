package me.ryzeon.bankingsystem.account.domain.model.exception;

public class AccountClosedException extends RuntimeException{

    public AccountClosedException() {
        super("Account is closed.");
    }
}
