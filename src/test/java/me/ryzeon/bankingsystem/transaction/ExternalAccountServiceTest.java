package me.ryzeon.bankingsystem.transaction;


import me.ryzeon.bankingsystem.account.interfaces.acl.AccountContextFacade;
import me.ryzeon.bankingsystem.transaction.application.internal.outboundservices.acl.ExternalAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExternalAccountServiceTest {

    @Mock
    private AccountContextFacade accountContextFacade;

    @InjectMocks
    private ExternalAccountService externalAccountService;

    @Test
    void testExistsAccountHolderWithAccountNumber_Exists() {
        when(accountContextFacade.existsAccountHolderWithAccountNumber("123")).thenReturn(true);
        assertTrue(externalAccountService.existsAccountHolderWithAccountNumber("123"));
    }

    @Test
    void testExistsAccountHolderWithAccountNumber_NotExists() {
        when(accountContextFacade.existsAccountHolderWithAccountNumber("unknown")).thenReturn(false);
        assertFalse(externalAccountService.existsAccountHolderWithAccountNumber("unknown"));
    }

    @Test
    void testGetAccountBalance() {
        when(accountContextFacade.getAccountBalance("123")).thenReturn(1000.0);
        assertEquals(1000.0, externalAccountService.getAccountBalance("123"));
    }

    @Test
    void testUpdateAccountBalance() {
        when(accountContextFacade.updateAccountBalance("123", 500.0)).thenReturn(true);
        assertTrue(externalAccountService.updateAccountBalance("123", 500.0));
    }
}