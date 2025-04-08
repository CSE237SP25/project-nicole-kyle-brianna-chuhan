package tests;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Scanner;
import bankapp.CreateAccount;
import bankapp.BankAccount;
import java.io.ByteArrayInputStream;
public class CreateAccountTests {
    private HashMap<String, String> userDatabase;
    private CreateAccount createAccount;
    @BeforeEach
    public void setUp() {
        userDatabase = new HashMap<>();
    }
    @Test
    public void testCreateNewAccount() {
        String input = "no\nnewUser\nnewPass\nchecking\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        createAccount = new CreateAccount(userDatabase, new Scanner(System.in));
        BankAccount account = createAccount.authenticateUser();
        
        assertNotNull(account);
        assertEquals("newUser", createAccount.getCurrentUsername());
        assertTrue(userDatabase.containsKey("newUser"));
    }
    @Test
    public void testLoginSuccess() {
        userDatabase.put("existingUser", "correctPass");
        String input = "yes\nexistingUser\ncorrectPass\nchecking\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        createAccount = new CreateAccount(userDatabase, new Scanner(System.in));
        BankAccount account = createAccount.authenticateUser();
        
        assertNotNull(account);
        assertEquals("existingUser", createAccount.getCurrentUsername());
    }
    @Test
    public void testLoginFailure() {
        userDatabase.put("existingUser", "correctPass");
        String input = "yes\nwrongUser\nwrongPass\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        createAccount = new CreateAccount(userDatabase, new Scanner(System.in));
        
        assertNull(createAccount.getCurrentUsername());
    }
    
    @Test
    public void testChangePassword() {
        userDatabase.put("user", "oldPass");
        String input = "yes\nuser\noldPass\nchecking\noldPass\nnewPass\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        createAccount = new CreateAccount(userDatabase, new Scanner(System.in));
        createAccount.authenticateUser();
        createAccount.changePassword();
        
        assertEquals("newPass", userDatabase.get("user"));
    }

    @Test
    public void testDeleteAccountSuccess() {
        userDatabase.put("user", "pass123");

        String input = "yes\nuser\npass123\nchecking\npass123\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        createAccount = new CreateAccount(userDatabase, new Scanner(System.in));
        createAccount.authenticateUser();
        createAccount.deleteAccount();

        assertFalse(userDatabase.containsKey("user"));
        assertNull(createAccount.getCurrentUsername());
    }

    @Test
    public void testDeleteAccountIncorrectPassword() {
        userDatabase.put("user", "pass123");

        String input = "yes\nuser\npass123\nchecking\nwrongpass\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        createAccount = new CreateAccount(userDatabase, new Scanner(System.in));
        createAccount.authenticateUser();
        createAccount.deleteAccount();

        // Ensure account is still in the system
        assertTrue(userDatabase.containsKey("user"));
        assertEquals("user", createAccount.getCurrentUsername());
    }
}


