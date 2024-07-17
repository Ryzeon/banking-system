package me.ryzeon.bankingsystem.account;

import me.ryzeon.bankingsystem.account.application.internal.commandservices.AccountCommandServiceImpl;
import me.ryzeon.bankingsystem.account.application.internal.queryservices.AccountQueryServiceImpl;
import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.CloseAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.CreateAccountCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountBalanceCommand;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountDetailsCommand;
import me.ryzeon.bankingsystem.account.domain.model.exception.AccountAlreadyClosedException;
import me.ryzeon.bankingsystem.account.domain.model.exception.AccountAlreadyExistsException;
import me.ryzeon.bankingsystem.account.domain.model.valueobjects.AccountInformation;
import me.ryzeon.bankingsystem.account.infrastructure.persistence.jpa.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountCommandServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountCommandServiceImpl accountCommandService;

    @InjectMocks
    private AccountQueryServiceImpl accountQueryService;

    private final Account mockAccount = new Account();

    {
        mockAccount.setId("123");
        mockAccount.setBalance(1000.00);
        mockAccount.setInformation(new AccountInformation("John", "Doe", "john.doe@mail.com", "123456789"));
        mockAccount.setAccountNumber("1234567890");
        mockAccount.setActiveAccount(true);
    }

    @Test
    void createAccount() {
        mockAccount.setId(null);
        when(accountRepository.save(mockAccount)).thenReturn(mockAccount);
        // Here we simulate the call to the service
        Account created = accountCommandService.handle(
                new CreateAccountCommand(
                        "1234567890",
                        1000.00,
                        "John",
                        "Doe",
                        "john.doe@mail.com",
                        "123456789"
                )
        ).get();

        assertNotNull(created);
        assertEquals("1234567890", created.getAccountNumber());
        assertEquals(1000.00, created.getBalance());

        // Verify
        verify(accountRepository).save(mockAccount);
    }

    @Test
    void createAccountError() {
        // Arrange
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(mockAccount));
        CreateAccountCommand command = new CreateAccountCommand(
                "1234567890",
                1000.00,
                "John",
                "Doe",
                "john.doe@mail.com",
                "123456789"
        );

        // Act & Assert
        assertThrows(AccountAlreadyExistsException.class, () -> accountCommandService.handle(command));
    }


    @Test
    void closeAccount() {
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(mockAccount));

        accountCommandService.handle(new CloseAccountCommand("1234567890"));

        assertFalse(mockAccount.isActiveAccount());

        // Verify
        verify(accountRepository).save(mockAccount);
    }

    @Test
    void closeAccountError() {
        mockAccount.setActiveAccount(false);
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(mockAccount));
        // Act & Assert
        Executable executable = () -> accountCommandService.handle(new CloseAccountCommand("1234567890"));

        // Assert
        assertThrows(AccountAlreadyClosedException.class, executable);
    }

    @Test
    void updateAccountDetails() {
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(mockAccount));

        accountCommandService.handle(new UpdateAccountDetailsCommand(
                "1234567890",
                "Jane",
                "Doe",
                 "jane@mail.com",
                "987654321"));

        assertEquals("Jane", mockAccount.getInformation().names());
        assertEquals("Doe", mockAccount.getInformation().lastNames());
        assertEquals("jane@mail.com", mockAccount.getInformation().email());
        assertEquals("987654321", mockAccount.getInformation().documentNumber());
    }

    @Test
    void updateAccountBalance() {
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(mockAccount));

        accountCommandService.handle(new UpdateAccountBalanceCommand("1234567890", 2000.00));

        assertEquals(2000.00, mockAccount.getBalance());
    }
}