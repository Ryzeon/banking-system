package me.ryzeon.bankingsystem.transaction.interfaces.res.resources;

public record CreateWithdrawalTransactionResource(String accountNumber, Double amount) {

    /**
     * Constructor for {@link CreateWithdrawalTransactionResource} which validates the provided parameters.
     *
     * @param accountNumber The account number for the withdrawal transaction. Must not be null or blank.
     * @param amount        The amount to withdraw. Must be greater than 0.
     * @throws IllegalArgumentException if the amount is less than or equal to 0, or if the account number is null or blank.
     */
    public CreateWithdrawalTransactionResource {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number must not be empty");
        }
    }
}