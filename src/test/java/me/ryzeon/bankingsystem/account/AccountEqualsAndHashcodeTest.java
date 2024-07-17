package me.ryzeon.bankingsystem.account;

import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.valueobjects.AccountInformation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountEqualsAndHashcodeTest {

    @Test
    void accountInitializationSetsCorrectDefaults() {
        Account account = new Account();
        assertEquals(0.0, account.getBalance());
        assertNotNull(account.getInformation());
        assertTrue(account.isActiveAccount());
        assertEquals("", account.getAccountNumber());
    }

    @Test
    void accountInitializationWithCommandSetsAllFieldsCorrectly() {
        CreateAccountCommand command = new CreateAccountCommand("1234567890", 1000.00, "John", "Doe", "john.doe@mail.com", "123456789");
        Account account = new Account(command);
        assertEquals(1000.00, account.getBalance());
        assertEquals("John", account.getInformation().names());
        assertEquals("Doe", account.getInformation().lastNames());
        assertEquals("john.doe@mail.com", account.getInformation().email());
        assertEquals("123456789", account.getInformation().documentNumber());
        assertEquals("1234567890", account.getAccountNumber());
        assertTrue(account.isActiveAccount());
    }

    @Test
    void closingAccountSetsActiveToFalseAndAssignsDeletedAt() {
        Account account = new Account();
        account.closeAccount();
        assertFalse(account.isActiveAccount());
        assertNotNull(account.getDeletedAt());
    }

    @Test
    void updatingInformationUpdatesAllInformationFields() {
        Account account = new Account();
        AccountInformation newInfo = new AccountInformation("Jane", "Roe", "jane.roe@mail.com", "987654321");
        account.updateInformation(newInfo);
        assertEquals("Jane", account.getInformation().names());
        assertEquals("Roe", account.getInformation().lastNames());
        assertEquals("jane.roe@mail.com", account.getInformation().email());
        assertEquals("987654321", account.getInformation().documentNumber());
    }
}