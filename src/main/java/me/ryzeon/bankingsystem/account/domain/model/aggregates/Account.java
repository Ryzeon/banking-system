package me.ryzeon.bankingsystem.account.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.valueobjects.AccountInformation;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@EqualsAndHashCode
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

    private Date deletedAt;

    /**
     * Default constructor initializing a new Account with default values.
     * Sets balance to 0.0, creates a new AccountInformation object, sets account number to an empty string,
     * and marks the account as active.
     */
    public Account() {
        this.balance = 0.0;
        this.information = new AccountInformation();
        this.accountNumber = "";
        this.activeAccount = true;
    }

    /**
     * Constructs a new Account using a CreateAccountCommand.
     * Initializes the account with the command's initial balance, account information, account number,
     * and sets the account as active.
     *
     * @param command The command containing the initial setup information for the account.
     */
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

    /**
     * Marks the account as inactive and sets the deletedAt timestamp to the current date.
     * This method is used for soft deletion of the account.
     */
    public void closeAccount() {
        this.activeAccount = false;
        this.deletedAt = new Date();
    }

    /**
     * Updates the account's information with the provided AccountInformation object.
     *
     * @param information The new account information to be set.
     */
    public void updateInformation(AccountInformation information) {
        this.information = information;
    }
}
