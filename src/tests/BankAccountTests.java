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
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(40));
    }
    @Test
    public void testNegativeWithdrawal() {
        BankAccount account = new BankAccount(null);
        account.deposit(50);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10));
    }
    
    
}







