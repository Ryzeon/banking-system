package me.ryzeon.bankingsystem.transaction.interfaces.res.resources;


public record CreateDepositTransactionResource(String accountNumber, Double amount) {

    /**
     * Represents a request to create a deposit transaction.
     * This record validates the provided account number and deposit amount upon creation.
     *
     * @param accountNumber The account number for the deposit transaction. Must not be null or blank.
     * @param amount        The amount to deposit. Must be greater than 0.
     * @throws IllegalArgumentException if the amount is less than or equal to 0, or if the account number is null or blank.
     */
    public CreateDepositTransactionResource {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number must not be empty");
        }
    }
}