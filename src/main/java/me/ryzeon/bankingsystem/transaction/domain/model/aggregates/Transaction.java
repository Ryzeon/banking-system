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

    public Transaction() {
        this.accountNumber = "";
        this.amount = 0.0;
        this.transactionType = TransactionType.DEPOSIT;
    }

    public Transaction(CreateTransactionCommand command, Double oldBalance, Double amount) {
        this.accountNumber = command.accountNumber();
        this.oldBalance = oldBalance;
        this.amount = amount;
        this.transactionType =  TransactionType.valueOf(command.transactionType());
    }
}
