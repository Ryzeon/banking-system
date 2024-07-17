package me.ryzeon.bankingsystem.account.domain.model.exception;

public class InvalidTransactionException extends RuntimeException{

    public InvalidTransactionException() {
        super("Invalid transaction.");
    }
}
