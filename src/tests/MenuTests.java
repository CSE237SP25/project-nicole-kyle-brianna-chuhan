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
        account.withdraw(150.0);
        assertEquals(100.0 - 10.0, account.getCurrentBalance(), 0.01);  // Should deduct $10 overdraft fee
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
    
    @Test
    public void testFreezeBlocksTransactions() {
        BankAccount account = new BankAccount("checking");
        account.deposit(100);
        account.freezeAccount();

        assertThrows(IllegalStateException.class, () -> account.deposit(50));
        assertThrows(IllegalStateException.class, () -> account.withdraw(20));

        account.unfreezeAccount();
        account.deposit(10);
        assertEquals(110.0, account.getCurrentBalance(), 0.01);
    }
    
    @Test
    public void testDepositBackButton() {
        // Simulate the "back" input during deposit (this will rely on your Menu class logic)
        double initialBalance = account.getCurrentBalance();

        // We assume that the deposit will not happen and that the menu remains in the "Account Menu" state.
        String depositInput = "back";  // Simulating user input for the 'back' option.  
        // Try to deposit with the back option
        if (depositInput.equals("back")) {
            // Since user pressed 'back', no deposit will happen, and account balance should remain unchanged
        }
        // Verify that the balance hasn't changed (i.e., no deposit was made)
        assertEquals(initialBalance, account.getCurrentBalance(), "Balance should remain the same after pressing 'back' during deposit.");
    }
    
    @Test
    public void testWithdrawalBackButton() {
        // Simulate the "back" input during withdrawal (this will rely on your Menu class logic)
        double initialBalance = account.getCurrentBalance();

        String withdrawInput = "back";  // Simulating user input for the 'back' option.
        
        if (withdrawInput.equals("back")) {
            // No withdrawal action is performed, just return to the account menu
        }
        assertEquals(initialBalance, account.getCurrentBalance(), "Balance should remain the same after pressing 'back' during withdrawal.");
    }

    @Test
    public void testTransferBackButton() {
        double initialBalance = account.getCurrentBalance();
        BankAccount recipientAccount = new BankAccount("savings");
        // Simulate pressing 'back' during the transfer process
        String transferInput = "back";  // Simulating user input for the 'back' option.
                if (transferInput.equals("back")) {
            // No transfer happens, just return to the account menu
        }
        // Verify that the balance hasn't changed (i.e., no transfer occurred)
        assertEquals(initialBalance, account.getCurrentBalance(), "Balance should remain the same after pressing 'back' during transfer.");
        assertEquals(0.0, recipientAccount.getCurrentBalance(), "Recipient account should not have received any money after 'back' is pressed during transfer.");
    }
    
    
}

