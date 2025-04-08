package tests;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.jupiter.api.Test;
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
    @Test
    public void testWithdrawMoreThanBalance() {
    	BankAccount account = new BankAccount(null);
        account.deposit(30);
        account.withdraw(40); // overdraft by $10 + $25 fee

        assertEquals(-35.0, account.getCurrentBalance(), 0.01);
    }
    @Test
    public void testNegativeWithdrawal() {
        BankAccount account = new BankAccount(null);
        account.deposit(50);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10));
    }
    @Test
    public void testOverdraftWithdrawal() {
        BankAccount account = new BankAccount(null);
        account.deposit(100);
        account.withdraw(150);

        // Expected: 100 - 150 - 25 = -75
        assertEquals(-75, account.getCurrentBalance(), 0.01);
    }
    @Test
    public void testMultipleOverdrafts() {
        BankAccount account = new BankAccount(null);
        account.deposit(100);
        account.withdraw(150); // -75
        account.withdraw(50);  // -75 - 50 - 25 = -150

        assertEquals(-150, account.getCurrentBalance(), 0.01);
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

    
}







