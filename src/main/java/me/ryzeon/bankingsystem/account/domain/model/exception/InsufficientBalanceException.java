package me.ryzeon.bankingsystem.account.domain.model.exception;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException() {
        super("Insufficient balance.");
    }
}
