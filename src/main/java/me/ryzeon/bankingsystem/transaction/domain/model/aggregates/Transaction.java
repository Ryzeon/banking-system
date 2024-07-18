package me.ryzeon.bankingsystem.transaction.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import me.ryzeon.bankingsystem.transaction.domain.model.commands.CreateTransactionCommand;
import me.ryzeon.bankingsystem.transaction.domain.model.valueobjects.TransactionType;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String accountNumber;

    private Double oldBalance;
    private Double amount;

    private TransactionType transactionType;

    /**
     * Default constructor for creating a transaction with default values.
     * Initializes a new transaction with an empty account number, a transaction amount of 0.0,
     * and sets the transaction type to DEPOSIT.
     */
    public Transaction() {
        this.accountNumber = "";
        this.amount = 0.0;
        this.transactionType = TransactionType.DEPOSIT;
    }

    /**
     * Constructs a new transaction using the provided {@link CreateTransactionCommand}, old balance, and amount.
     * This constructor is used to create a transaction with specific details such as the account number,
     * old balance, transaction amount, and transaction type derived from the command.
     *
     * @param command    The command containing the details for the transaction.
     * @param oldBalance The old balance before the transaction is applied.
     * @param amount     The amount of the transaction.
     */
    public Transaction(CreateTransactionCommand command, Double oldBalance, Double amount) {
        this.accountNumber = command.accountNumber();
        this.oldBalance = oldBalance;
        this.amount = amount;
        this.transactionType = TransactionType.valueOf(command.transactionType());
    }
}
