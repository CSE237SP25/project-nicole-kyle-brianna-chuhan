package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankapp.BankAccount;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTests {
    private BankAccount account;

    @BeforeEach
    public void setUp() {
        account = new BankAccount(null);
    }

    @Test
    public void testDepositValidAmount() {
        account.deposit(300.0);
        assertEquals(300.0, account.getCurrentBalance(), 0.01);
    }

    @Test
    public void testDepositNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-50.0);
        });
    }

    @Test
    public void testWithdrawValidAmount() {
        account.deposit(200.0);
        account.withdraw(100.0);
        assertEquals(100.0, account.getCurrentBalance(), 0.01);
    }

    @Test
    public void testWithdrawNegativeAmount() {
    	assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-20.0);
        });
    }

    @Test
    public void testWithdrawMoreThanBalance() {
        account.deposit(100.0);
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(150.0);
        });
    }

    @Test
    public void testCheckBalanceAfterTransactions() {
        account.deposit(500.0);
        account.withdraw(120.0);
        assertEquals(380.0, account.getCurrentBalance(), 0.01);
    }
    
    @Test
    public void testTransferBetweenAccounts() {
        BankAccount sender = new BankAccount("checking");
        BankAccount receiver = new BankAccount("savings");

        sender.deposit(500.0);
        sender.transferTo(receiver, 200.0);

        assertEquals(300.0, sender.getCurrentBalance(), 0.01);
        assertEquals(200.0, receiver.getCurrentBalance(), 0.01);
    }
}

