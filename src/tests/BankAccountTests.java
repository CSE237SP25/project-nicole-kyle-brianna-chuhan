package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import bankapp.BankAccount;

public class BankAccountTests {

    
    @Test
    public void testAccountCreation() {
        BankAccount account = new BankAccount("mypassword");
        assertNotNull(account);
        assertTrue(account.getAccountId() >= 1000);
        assertEquals(0.0, account.getCurrentBalance("mypassword"), 0.005);
    }

   
    @Test
    public void testSimpleDeposit() {
        
        BankAccount account = new BankAccount("mypassword");
        account.deposit(25);
        assertEquals(25.0, account.getCurrentBalance("mypassword"), 0.005);
    }

   
    @Test
    public void testNegativeDeposit() {
        
        BankAccount account = new BankAccount("mypassword");

        try {
            account.deposit(-25);
            fail("IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e != null);
            assertEquals("You must deposit more than 0", e.getMessage());
        }
    }

    @Test
    public void testInvalidPassword() {
        BankAccount account = new BankAccount("mypassword");
        account.deposit(100);

        double balance = account.getCurrentBalance("wrongpassword");

        assertEquals(-1, balance);
    }

    @Test
    public void testValidPassword() {
        BankAccount account = new BankAccount("mypassword");
        account.deposit(100);

        double balance = account.getCurrentBalance("mypassword");

        assertEquals(100.0, balance);
    }

    @Test
    public void testMultipleDeposits() {
        BankAccount account = new BankAccount("mypassword");
        account.deposit(50);
        account.deposit(25);
        account.deposit(75);

        assertEquals(150.0, account.getCurrentBalance("mypassword"), 0.005);
    }

    @Test
    public void testGetAccountId() {
        BankAccount account = new BankAccount("mypassword");
        int accountId = account.getAccountId();
        assertTrue(accountId >= 1000);
    }
}



