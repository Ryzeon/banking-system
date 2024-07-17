package me.ryzeon.bankingsystem.account;

import me.ryzeon.bankingsystem.account.domain.model.aggregates.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AccountEqualsHashCodeTest {

    @Test
    void testEqualsTruePath() {
        Account account1 = new Account();
        account1.setId("123");
        Account account2 = new Account();
        account2.setId("123");

        assertEquals(account1, account2);
    }

    @Test
    void testEqualsFalsePath() {
        Account account1 = new Account();
        account1.setId("123");
        Account account2 = new Account();
        account2.setId("456");

        assertNotEquals(account1, account2);
    }

    @Test
    void testHashCodeConsistency() {
        Account account = new Account();
        account.setId("123");
        int initialHashCode = account.hashCode();

        assertEquals(initialHashCode, account.hashCode());
        assertEquals(initialHashCode, account.hashCode());
    }

    @Test
    void testHashCodeEquality() {
        Account account1 = new Account();
        account1.setId("123");
        Account account2 = new Account();
        account2.setId("123");

        assertEquals(account1.hashCode(), account2.hashCode());
    }

    @Test
    void testHashCodeInequality() {
        Account account1 = new Account();
        account1.setId("123");
        Account account2 = new Account();
        account2.setId("456");

        assertNotEquals(account1.hashCode(), account2.hashCode());
    }
}