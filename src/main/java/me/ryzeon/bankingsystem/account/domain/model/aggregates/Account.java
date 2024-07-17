package me.ryzeon.bankingsystem.account.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Data;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.valueobjects.AccountInformation;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Double balance;

    @Embedded
    private AccountInformation information;

    private String accountNumber;

    private boolean activeAccount;

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    private Date deletedAt; // Soft delete

    public Account() {
        this.balance = 0.0;
        this.information = new AccountInformation();
        this.accountNumber = "";
        this.activeAccount = true;
    }

    public Account(CreateAccountCommand command) {
        this.balance = command.initialBalance();
        this.information = new AccountInformation(
                command.names(),
                command.lastNames(),
                command.email(),
                command.documentNumber()
        );
        this.accountNumber = command.accountNumber();
        this.activeAccount = true;
    }

    public void closeAccount() {
        this.activeAccount = false;
        this.deletedAt = new Date();
    }

    public void updateInformation(AccountInformation information) {
        this.information = information;
    }
}
