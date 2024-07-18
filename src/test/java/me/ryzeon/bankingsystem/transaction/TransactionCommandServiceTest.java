package me.ryzeon.bankingsystem.transaction;

import me.ryzeon.bankingsystem.account.domain.model.exception.AccountNotFoundException;
import me.ryzeon.bankingsystem.account.domain.model.exception.InsufficientBalanceException;
import me.ryzeon.bankingsystem.account.domain.model.exception.InvalidTransactionException;
import me.ryzeon.bankingsystem.transaction.application.internal.commandservices.TransactionCommandServiceImpl;
import me.ryzeon.bankingsystem.transaction.application.internal.outboundservices.acl.ExternalAccountService;
import me.ryzeon.bankingsystem.transaction.domain.model.aggregates.Transaction;
import me.ryzeon.bankingsystem.transaction.domain.model.commands.CreateTransactionCommand;
import me.ryzeon.bankingsystem.transaction.domain.model.valueobjects.TransactionType;
import me.ryzeon.bankingsystem.transaction.infrastructure.persistence.jpa.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionCommandServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ExternalAccountService externalAccountService;

    @InjectMocks
    private TransactionCommandServiceImpl transactionCommandService;

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(externalAccountService.existsAccountHolderWithAccountNumber("validAccountNumber")).thenReturn(true);
        Mockito.lenient().when(externalAccountService.existsAccountHolderWithAccountNumber("invalidAccountNumber")).thenReturn(false);

        Mockito.lenient().when(externalAccountService.getAccountBalance("validAccountNumber")).thenReturn(1000.0);

        Mockito.lenient().when(externalAccountService.updateAccountBalance(anyString(), anyDouble())).thenReturn(true);
        Mockito.lenient().when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testHandleCreateTransactionWithValidData() {
        CreateTransactionCommand command = new CreateTransactionCommand("validAccountNumber", 500.0, "DEPOSIT");
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> transactionCommandService.handle(command));
    }

    @Test
    void testHandleInvalidTransactionType() {
        CreateTransactionCommand command = new CreateTransactionCommand("validAccountNumber", 500.0, "INVALID");

        assertThrows(InvalidTransactionException.class, () -> transactionCommandService.handle(command));
    }

    @Test
    void testHandleWithdrawalTransactionWithSufficientBalance() {
        double accountBalance = 1000.0;
        double transactionAmount = 500.0;

        when(externalAccountService.getAccountBalance("validAccountNumber")).thenReturn(accountBalance);

        CreateTransactionCommand command = new CreateTransactionCommand("validAccountNumber", transactionAmount, "WITHDRAWAL");

        assertDoesNotThrow(() -> transactionCommandService.handle(command));
    }

    @Test
    void testHandleDepositTransaction() {
        double accountBalance = 0.0;
        double transactionAmount = 500.0;
        when(externalAccountService.getAccountBalance("validAccountNumber")).thenReturn(accountBalance);

        CreateTransactionCommand command = new CreateTransactionCommand("validAccountNumber", transactionAmount, "DEPOSIT");

        assertDoesNotThrow(() -> transactionCommandService.handle(command));
    }

    @Test
    void createTransactionTest() {
        CreateTransactionCommand command = new CreateTransactionCommand("123456789", 100.0, "DEPOSIT");
        Double oldBalance = 500.0;
        Double newAmount = 100.0;

        Transaction transaction = new Transaction(command, oldBalance, newAmount);
        transaction.setId("123");

        assertEquals("123456789", transaction.getAccountNumber());
        assertEquals(oldBalance, transaction.getOldBalance());
        assertEquals(newAmount, transaction.getAmount());
        assertEquals(TransactionType.DEPOSIT, transaction.getTransactionType());
        assertEquals("123", transaction.getId());
    }

    @Test
    void testHandleCreateTransactionWithInvalidAccount() {
        CreateTransactionCommand command = new CreateTransactionCommand("invalidAccountNumber", 500.0, "DEPOSIT");

        assertThrows(AccountNotFoundException.class, () -> transactionCommandService.handle(command));
    }

    @Test
    void testHandleCreateTransactionWithInsufficientBalance() {
        CreateTransactionCommand command = new CreateTransactionCommand("validAccountNumber", 1500.0, "WITHDRAWAL");

        assertThrows(InsufficientBalanceException.class, () -> transactionCommandService.handle(command));
    }

    @Test
    void testDepositTransactionWithSufficientBalance() {
        CreateTransactionCommand command = new CreateTransactionCommand("validAccountNumber", 200.0, "DEPOSIT");
        when(externalAccountService.updateAccountBalance(anyString(), anyDouble())).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> transactionCommandService.handle(command));
    }

    @Test
    void testWithdrawalTransactionWithInsufficientBalance() {
        CreateTransactionCommand command = new CreateTransactionCommand("validAccountNumber", 2000.0, "WITHDRAWAL");
        when(externalAccountService.getAccountBalance("validAccountNumber")).thenReturn(1000.0);

        assertThrows(InsufficientBalanceException.class, () -> transactionCommandService.handle(command));
    }

    @Test
    void testWithdrawalTransactionSuccessful() {
        CreateTransactionCommand command = new CreateTransactionCommand("validAccountNumber", 500.0, "WITHDRAWAL");
        when(externalAccountService.getAccountBalance("validAccountNumber")).thenReturn(1000.0);
        when(externalAccountService.updateAccountBalance(anyString(), anyDouble())).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> transactionCommandService.handle(command));
    }

}