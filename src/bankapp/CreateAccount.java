package bankapp;
import java.util.HashMap;
import java.util.Scanner;
public class CreateAccount {
   private HashMap<String, String> userDatabase;
   private HashMap<String, BankAccount> accountMap = new HashMap<>();
   private Scanner scanner;
   private String currentUsername;
   private String currentPassword;
   public CreateAccount(HashMap<String, String> userDatabase, Scanner scanner) {
       this.userDatabase = userDatabase;
       this.scanner = scanner;
   }
   public BankAccount authenticateUser() {
       System.out.println("Welcome! Do you have an account? (yes/no)");
       String response = scanner.nextLine().trim().toLowerCase();
       if (response.equals("yes")) {
           return login();
       } else {
           return createNewAccount();
       }
   }
   private BankAccount login() {
       System.out.print("Enter your username: ");
       String username = scanner.nextLine();
       System.out.print("Enter your password: ");
       String password = scanner.nextLine();
       if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
           System.out.println("Login successful. Welcome, " + username + "!");
           currentUsername = username;
           currentPassword = password;
           if (accountMap.containsKey(username)) {
               return accountMap.get(username);
           } else {
               return chooseAccountType();
           }
       } else {
           System.out.println("Invalid credentials. Try again.");
           return login();
       }
   }
   private BankAccount createNewAccount() {
   	System.out.print("Create a username: ");
       String username = scanner.nextLine();
       if (userDatabase.containsKey(username)) {
           System.out.println("Username already exists. Try logging in.");
           return login();
       }
       System.out.print("Create a password: ");
       String password = scanner.nextLine();
       userDatabase.put(username, password);
       currentUsername = username;
       currentPassword = password;
       System.out.println("Account created successfully!");
       return chooseAccountType();
   }
   private BankAccount chooseAccountType() {
       System.out.print("Choose account type (checking/savings): ");
       String type = scanner.nextLine().trim().toLowerCase();
       while (!type.equals("checking") && !type.equals("savings")) {
           System.out.print("Invalid type. Please enter 'checking' or 'savings': ");
           type = scanner.nextLine().trim().toLowerCase();
       }
       BankAccount newAccount = new BankAccount(type);
       accountMap.put(currentUsername, newAccount);
       System.out.println(type + " account created successfully.");
       return newAccount;
   }
   public void changePassword() {
       System.out.print("Enter current password: ");
       String input = scanner.nextLine();
       if (!input.equals(currentPassword)) {
           System.out.println("Incorrect password.");
           return;
       }
       System.out.print("Enter new password: ");
       String newPassword = scanner.nextLine();
       userDatabase.put(currentUsername, newPassword);
       currentPassword = newPassword;
       System.out.println("Password updated successfully.");
   }
   // Getter for current username
   public String getCurrentUsername() {
       return currentUsername;
   }
   
   public BankAccount getAccountByUsername(String username) { 
       return accountMap.get(username); 
   } 

   public void deleteAccount() {
	    System.out.print("Enter your password to confirm you want to delete your account: ");
	    String input = scanner.nextLine();

	    if (!input.equals(currentPassword)) {
	        System.out.println("Incorrect password. Account deletion cancelled.");
	        return;
	    }

	    userDatabase.remove(currentUsername);
	    System.out.println("Account '" + currentUsername + "' has been deleted. Please see your local bank to collect your remaining funds.");

	    currentUsername = null;
	    currentPassword = null;
	}
}

