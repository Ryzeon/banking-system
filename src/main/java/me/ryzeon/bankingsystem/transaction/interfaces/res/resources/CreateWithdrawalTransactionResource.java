package me.ryzeon.bankingsystem.transaction.interfaces.res.resources;

public record CreateWithdrawalTransactionResource (String accountNumber, Double amount) {

    public CreateWithdrawalTransactionResource {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number must not be empty");
        }
    }
}