package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import bankapp.BankAccount;

public class BankAccountTests {

	@Test
	public void testSimpleDeposit() {
		//1. Create objects to be tested
		BankAccount account = new BankAccount();
		
		//2. Call the method being tested
		account.deposit(25);
		
		//3. Use assertions to verify results
		assertEquals(account.getCurrentBalance(), 25.0, 0.005);
	}
	
	@Test
	public void testNegativeDeposit() {
		//1. Create object to be tested
		BankAccount account = new BankAccount();

		try {
			account.deposit(-25);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e != null);
		}
	}
	
	// Deposit tests
	
	 @Test
    public void testSimpleWithdrawal() {
        // 1. Create object to be tested
        BankAccount account = new BankAccount();
        account.deposit(50);
        // 2. Call the method being tested
        account.withdraw(20);
        // 3. Use assertions to verify results
        assertEquals(30.0, account.getCurrentBalance(), 0.005);
    }
	 
    @Test
    public void testWithdrawExactBalance() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        account.withdraw(50);
        assertEquals(0.0, account.getCurrentBalance(), 0.005);
    }
    
    @Test
    public void testWithdrawMoreThanBalance() {
        BankAccount account = new BankAccount();
        account.deposit(30);
        try {
            account.withdraw(40);
            fail(); // This should fail because withdrawal is greater than balance
        } catch (IllegalArgumentException e) {
            assertEquals("Insufficient funds.", e.getMessage());
        }
    }
    
    @Test
    public void testNegativeWithdrawal() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        try {
            account.withdraw(-10);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Withdrawal amount must be non-negative.", e.getMessage());
        }
    }
    
    @Test
    public void testBulkWithdrawal() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        double[] amounts = {20, 30, 10};
        account.withdraw(amounts);
        assertEquals(40.0, account.getCurrentBalance(), 0.005);
    }
    
    @Test
    public void testBulkWithdrawalInsufficientFunds() {
        BankAccount account = new BankAccount();
        account.deposit(50);
        double[] amounts = {20, 40}; // Exceeds balance
        try {
            account.withdraw(amounts);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Insufficient funds for bulk withdrawal.", e.getMessage());
        }
    }
    
    @Test
    public void testBulkWithdrawalWithNegativeAmount() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        double[] amounts = {20, -10, 30}; // Contains a negative withdrawal
        try {
            account.withdraw(amounts);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("All withdrawal amounts must be non-negative.", e.getMessage());
        }	
	}
    
}
