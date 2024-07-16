package me.ryzeon.bankingsystem.account.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Data;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.valueobjects.AccountInformation;

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

    public Account() {
        this.balance = 0.0;
        this.information = new AccountInformation();
        this.accountNumber = "";
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
    }

    public void updateInformation(AccountInformation information) {
        this.information = information;
    }
}
