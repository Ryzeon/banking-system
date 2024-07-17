package me.ryzeon.bankingsystem.account;

import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import me.ryzeon.bankingsystem.account.domain.model.commands.UpdateAccountBalanceCommand;
import me.ryzeon.bankingsystem.account.domain.model.queries.GetAccountHolderByAccountNumberQuery;
import me.ryzeon.bankingsystem.account.domain.services.AccountCommandService;
import me.ryzeon.bankingsystem.account.domain.services.AccountQueryService;
import me.ryzeon.bankingsystem.account.interfaces.acl.AccountContextFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountContextFacadeTest {

    @Mock
    private AccountQueryService accountQueryService;

    @Mock
    private AccountCommandService accountCommandService;

    @InjectMocks
    private AccountContextFacade accountContextFacade;

    @Test
    void testExistsAccountHolderWithAccountNumber() {
        when(accountQueryService.handle(new GetAccountHolderByAccountNumberQuery("123"))).thenReturn(Optional.of(new Account()));
        assertTrue(accountContextFacade.existsAccountHolderWithAccountNumber("123"));
        when(accountQueryService.handle(new GetAccountHolderByAccountNumberQuery("unknown"))).thenReturn(Optional.empty());
        assertFalse(accountContextFacade.existsAccountHolderWithAccountNumber("unknown"));
    }

    @Test
    void testGetAccountBalance() {
        Account account = new Account();
        account.setBalance(100.0);
        when(accountQueryService.handle(new GetAccountHolderByAccountNumberQuery("123"))).thenReturn(Optional.of(account));
        assertEquals(100.0, accountContextFacade.getAccountBalance("123"));
    }

    @Test
    void testUpdateAccountBalance() {
        doNothing().when(accountCommandService).handle(any(UpdateAccountBalanceCommand.class));
        accountContextFacade.updateAccountBalance("123", 200.0);
        verify(accountCommandService).handle(new UpdateAccountBalanceCommand("123", 200.0));
    }

    @Test
    void testGetAccountBalanceWithNonExistentAccountNumber() {
        String nonExistentAccountNumber = "randomNumberAcc";
        when(accountQueryService.handle(new GetAccountHolderByAccountNumberQuery(nonExistentAccountNumber))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> accountContextFacade.getAccountBalance(nonExistentAccountNumber));
    }
}