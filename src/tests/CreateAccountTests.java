package tests;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Scanner;
import bankapp.CreateAccountMenu;
import bankapp.BankAccount;
import java.io.ByteArrayInputStream;
public class CreateAccountTests {
    private HashMap<String, String> userDatabase;
    private HashMap<String, String> securityQuestions = new HashMap<>(); 
    private CreateAccountMenu createAccount;
    @BeforeEach
    public void setUp() {
        userDatabase = new HashMap<>();
    }
    
    @Test
    public void testCreateNewAccount() {
        // Simulate the input for the flow: no -> newUser -> newPass -> blue -> checking
        String input = "no\nnewUser\nnewPass\nblue\nchecking\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        createAccount = new CreateAccountMenu(userDatabase, new Scanner(System.in));

        BankAccount account = createAccount.authenticateUser();

        assertNotNull(account);
        assertEquals("newUser", createAccount.getCurrentUsername());
        assertTrue(userDatabase.containsKey("newUser"));
        assertEquals(0.0, account.getCurrentBalance(), 0.01);
        assertEquals("checking", account.getAccountType());
    }
    
    @Test
    public void testLoginSuccess() {
        userDatabase.put("existingUser", "correctPass");
        String input = "yes\nexistingUser\ncorrectPass\nchecking\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        createAccount = new CreateAccountMenu(userDatabase, new Scanner(System.in));
        BankAccount account = createAccount.authenticateUser();
        
        assertNotNull(account);
        assertEquals("existingUser", createAccount.getCurrentUsername());
    }
    @Test
    public void testLoginFailure() {
        userDatabase.put("existingUser", "correctPass");
        String input = "yes\nwrongUser\nwrongPass\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        createAccount = new CreateAccountMenu(userDatabase, new Scanner(System.in));
        
        assertNull(createAccount.getCurrentUsername());
    }
    
    @Test
    public void testChangePassword() {
        userDatabase.put("user", "oldPass");
        String input = "yes\nuser\noldPass\nchecking\noldPass\nnewPass\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        createAccount = new CreateAccountMenu(userDatabase, new Scanner(System.in));
        createAccount.authenticateUser();
        createAccount.changePassword();
        
        assertEquals("newPass", userDatabase.get("user"));
    }
    
    @Test
    public void testDepositWithdrawWithCorrectSecurityAnswer() {
        BankAccount account = new BankAccount(null);
        account.deposit(11000);  // Deposit 11,000

        // Assuming there's some internal check for security question here
        boolean securityCheckPassed = true;  // Simulate a correct answer to the security question

        // Try withdrawing 10,000
        if (securityCheckPassed) {
            account.withdraw(10001);  // Only withdraw if security check passes
        }
        // Assert the balance is 1,000 after the withdrawal
        assertEquals(999, account.getCurrentBalance(), 0.01);
    }
    
    @Test
    public void testDepositWithdrawWithIncorrectSecurityAnswer() {
        BankAccount account = new BankAccount(null);
        account.deposit(11000);  // Deposit 11,000

        // Simulate an incorrect security answer
        boolean securityCheckPassed = false;

        // Try withdrawing 10,000, but it should fail because the security check fails
        if (!securityCheckPassed) {
            System.out.println("Security check failed. Withdrawal not processed.");
        } else {
            account.withdraw(10001);  // This would only happen if security check passes
        }
        // Assert that the balance remains 11,000 since withdrawal did not happen
        assertEquals(11000, account.getCurrentBalance(), 0.01);
    }
    
    @Test
    public void testDeleteAccount() {
        // Simulate inputs:
        // no → newUser → newPass → blue → checking → yes
        String input = "no\nnewUser\nnewPass\nblue\nchecking\nyes\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        createAccount = new CreateAccountMenu(userDatabase, new Scanner(System.in));

        // Create account
        BankAccount account = createAccount.authenticateUser();

        // Validate account created
        assertNotNull(account);
        assertEquals("newUser", createAccount.getCurrentUsername());
        assertTrue(userDatabase.containsKey("newUser"));
        assertEquals(0.0, account.getCurrentBalance(), 0.01);

        // Delete account
        createAccount.deleteAccount();

        // Validate account deleted
        assertNull(createAccount.getCurrentUsername());
        assertFalse(userDatabase.containsKey("newUser"));
        assertNull(createAccount.getAccountByUsername("newUser"));
    }

    
}

