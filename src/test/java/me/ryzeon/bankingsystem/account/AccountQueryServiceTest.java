package me.ryzeon.bankingsystem.account;

import me.ryzeon.bankingsystem.account.application.internal.queryservices.AccountQueryServiceImpl;
import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountByIdQuery;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountHolderByAccountNumberQuery;
import me.ryzeon.bankingsystem.account.domain.model.valueobjects.AccountInformation;
import me.ryzeon.bankingsystem.account.infrastructure.persistence.jpa.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountQueryServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountQueryServiceImpl accountQueryService;

    private final Account mockAccount = new Account();
    {
        mockAccount.setId("123");
        mockAccount.setBalance(1000.00);
        mockAccount.setInformation(new AccountInformation("John", "Doe", "john.doe@mail.com", "123456789"));
        mockAccount.setAccountNumber("1234567890");
    }

    @Test
    void testFindAccountById() {
        when(accountRepository.findById("123")).thenReturn(Optional.of(mockAccount));
        // Here we simulate the call to the service
        Account found = accountQueryService.handle(new GetAccountByIdQuery("123")).get();

        // Assert
        assertNotNull(found);
        assertEquals("123", found.getId());
        assertEquals(1000.00, found.getBalance());

        // Verify
        verify(accountRepository).findById("123");
    }

    @Test
    void testFindByAccountNumber() {
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(mockAccount));
        // Here we simulate the call to the service
        Account found = accountQueryService.handle(new GetAccountHolderByAccountNumberQuery("1234567890")).get();

        // Assert
        assertNotNull(found);
        assertEquals("123", found.getId());
        assertEquals(1000.00, found.getBalance());

        // Verify
        verify(accountRepository).findByAccountNumber("1234567890");
    }

    @Test
    void testAccountNotFound() {
        when(accountRepository.findById("123")).thenReturn(Optional.empty());
        // Here we simulate the call to the service
        Account found = accountQueryService.handle(new GetAccountByIdQuery("123")).orElse(null);

        // Assert
        assertNull(found);

        // Verify
        verify(accountRepository).findById("123");
    }
}