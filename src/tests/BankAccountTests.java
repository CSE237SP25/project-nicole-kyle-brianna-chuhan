package tests;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import java.util.List;
import bankapp.BankAccount;
public class BankAccountTests {
	@Test
	public void testSimpleDeposit() {
		//1. Create objects to be tested
		BankAccount account = new BankAccount(null);
		
		//2. Call the method being tested
		account.deposit(25);
		
		//3. Use assertions to verify results
		assertEquals(account.getCurrentBalance(), 25.0, 0.005);
	}
	
	@Test
    public void testNegativeDeposit() {
        // 1. Create object to be tested
        BankAccount account = new BankAccount(null);
        // 2. Expect an exception when depositing a negative amount
        try {
            account.deposit(-25);
            fail("Expected IllegalArgumentException for negative deposit");
        } catch (IllegalArgumentException e) {
            // Exception was expected, test passes
        }
    }
    @Test
    public void testWithdrawal() {
        BankAccount account = new BankAccount(null);
        account.deposit(50);
        account.withdraw(20);
        assertEquals(30.0, account.getCurrentBalance(), 0.005);
    }
    
    public void testWithdrawMoreThanBalanceWithOverdraftFee() {
        BankAccount account = new BankAccount(null);
        account.deposit(30);
        account.withdraw(40);  // Should not throw, should apply overdraft fee
        // Balance should remain the same
        assertEquals(30.0, account.getCurrentBalance(), 0.005);
        // Check transaction history to confirm overdraft fee applied
        List<String> history = account.getTransactionHistory();
        assertEquals(2, history.size());  // 1 deposit + 1 overdraft attempt
        assertTrue(history.get(1).contains("Overdraft fee applied"));
    }
    
    @Test
    public void testNegativeWithdrawal() {
        BankAccount account = new BankAccount(null);
        account.deposit(50);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10));
    }
    
    
    @Test
    public void testSimpleTransfer() {
        // Create two accounts
        BankAccount account1 = new BankAccount(null);
        BankAccount account2 = new BankAccount(null);
        
        // Deposit into account1
        account1.deposit(100);
        
        // Perform transfer from account1 to account2
        account1.transferTo(account2, 50);
        
        // Check the balances after transfer
        assertEquals(50.0, account1.getCurrentBalance(), 0.005);  // account1 should have 50 left
        assertEquals(50.0, account2.getCurrentBalance(), 0.005);  // account2 should have 50
    }
    
    @Test
    public void testTransferInsufficientFunds() {
        BankAccount account1 = new BankAccount(null);
        BankAccount account2 = new BankAccount(null);
        account1.deposit(30);
        // Expect an exception when trying to transfer more than available balance
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account1.transferTo(account2, 50);
        });
        assertEquals("Insufficient funds for transfer.", exception.getMessage());
        // Ensure balances remain unchanged
        assertEquals(30.0, account1.getCurrentBalance(), 0.005);
        assertEquals(0.0, account2.getCurrentBalance(), 0.005);
    }
    @Test
    public void testSetSpendingLimitAndWithdrawUnderLimit() {
        BankAccount account = new BankAccount(null);
        account.deposit(200);
        account.setSpendingLimit(100);
        // Withdrawing an amount below limit (e.g., 80) should succeed
        account.withdraw(80);
        assertEquals(120, account.getCurrentBalance(), 0.01);
    }
    @Test
    public void testWithdrawOverSpendingLimit() {
        BankAccount account = new BankAccount(null);
        account.deposit(200);
        account.setSpendingLimit(100);
        // Attempting to withdraw more than 100 should fail
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(150));
        assertEquals(200, account.getCurrentBalance(), 0.01);
    }
    @Test
    public void testSetZeroOrNegativeSpendingLimitThrowsException() {
        BankAccount account = new BankAccount(null);
        
        // Setting limit to zero or negative should fail
        assertThrows(IllegalArgumentException.class, () -> account.setSpendingLimit(0));
        assertThrows(IllegalArgumentException.class, () -> account.setSpendingLimit(-50));
    }
    @Test
    public void testClearSpendingLimit() {
        BankAccount account = new BankAccount(null);
        account.deposit(200);
        account.setSpendingLimit(100);
        account.clearSpendingLimit();
        // Now should be able to withdraw any amount (assuming enough balance)
        account.withdraw(150);
        assertEquals(50, account.getCurrentBalance(), 0.01);
    }
    @Test
    public void testSpendingLimitForTransfer() {
        BankAccount account1 = new BankAccount(null);
        BankAccount account2 = new BankAccount(null);
        
        account1.deposit(300);
        
        // Enforce the limit on transfers
        account1.setSpendingLimit(100);
        assertThrows(IllegalArgumentException.class, () -> account1.transferTo(account2, 150));
        assertEquals(300, account1.getCurrentBalance(), 0.01);
        assertEquals(0, account2.getCurrentBalance(), 0.01);
        account1.transferTo(account2, 100);
        assertEquals(200, account1.getCurrentBalance(), 0.01);
        assertEquals(100, account2.getCurrentBalance(), 0.01);
    }
    
    
     @Test
     public void testBalanceBelow50Alert() {
        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        try {
            BankAccount account = new BankAccount(null);
            account.deposit(60);
            outContent.reset();
            // Withdraw 20 so balance = 40 (balance below $50)
            account.withdraw(20);
            String consoleOutput = outContent.toString();
            // Check that the alert message was printed
            assertTrue(
                "Expected alert message when balance goes below $50.",
                consoleOutput.contains("!Alert! Your account balance is below $50.")
            );
            // Also verify the new balance
            assertEquals(40.0, account.getCurrentBalance(), 0.01);
        } finally {
            System.setOut(originalOut);
        }
    }
    @Test
    public void testNoAlertWhenBalanceStill50OrMore() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        try {
            BankAccount account = new BankAccount(null);
            account.deposit(100);
            outContent.reset();
            // Withdraw 50 so balance = 50 (balance >= $50)
            account.withdraw(50);
            String consoleOutput = outContent.toString();
            assertFalse(
                "No alert should be printed if balance remains >= $50.",
                consoleOutput.contains("Alert: Your account balance is below $50.")
            );
            // Also verify the balance
            assertEquals(50.0, account.getCurrentBalance(), 0.01);
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    public void testSetValidSpendingLimit() {
        BankAccount account = new BankAccount(null);
        account.setSpendingLimit(500.00);
        assertEquals(500.00, account.getSpendingLimit(), 0.005);
    }
    
    @Test
    public void testSetInvalidSpendingLimit() {
        BankAccount account = new BankAccount(null);
        assertThrows(IllegalArgumentException.class, () -> account.setSpendingLimit(0));
        assertThrows(IllegalArgumentException.class, () -> account.setSpendingLimit(-100));
    }
    
}
