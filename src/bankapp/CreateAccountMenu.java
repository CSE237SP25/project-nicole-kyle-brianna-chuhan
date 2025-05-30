package bankapp;

import java.util.HashMap;
import java.util.Scanner;

public class CreateAccountMenu {
    private HashMap<String, String> userDatabase;
    private HashMap<String, BankAccount> accountMap = new HashMap<>();
    private HashMap<String, String> securityQuestions = new HashMap<>(); 
    private Scanner scanner;
    private String currentUsername;
    private String currentPassword;

    public CreateAccountMenu(HashMap<String, String> userDatabase, Scanner scanner) {
        this.userDatabase = userDatabase;
        this.scanner = scanner;
    }

    // Entry point for login or registration
    public BankAccount authenticateUser() {
        System.out.println("Welcome! Do you have an account? (yes/no)");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes") ? login() : createNewAccount();
    }

    // Handles user login
    private BankAccount login() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            System.out.println("Login successful. Welcome, " + username + "!");
            currentUsername = username;
            currentPassword = password;
            return accountMap.containsKey(username) ? accountMap.get(username) : chooseAccountType();
        } else {
            System.out.println("Invalid credentials. Try again.");
            return login();
        }
    }

    // Handles account creation
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

        // Set the security question for the new account
        System.out.print("Set your security question answer (What is your favorite color?): ");
        String answer = scanner.nextLine();
        securityQuestions.put(username, answer); 

        return chooseAccountType();
    }

    // Lets user choose between checking or savings
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

    // Lets user update their password
    public void changePassword() {
        System.out.print("Enter current password: ");
        String input = scanner.nextLine();
        if (!input.equals(currentPassword)) {
            System.out.println("Incorrect password.");
            return;
        }

        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        
        if (newPassword.equals(currentPassword)) {
        System.out.println("New password cannot be the same as the current password.");
        return;
        }
        userDatabase.put(currentUsername, newPassword);
        currentPassword = newPassword;
        System.out.println("Password updated successfully.");
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public BankAccount getAccountByUsername(String username) {
        return accountMap.get(username);
    }

    // Deletes current user account after confirmation
    public void deleteAccount() {
        System.out.print("Are you sure you want to delete your account? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();

        if (!input.equals("yes")) {
            System.out.println("Account deletion cancelled.");
            return;
        }

        userDatabase.remove(currentUsername);
        accountMap.remove(currentUsername);
        System.out.println("Account for '" + currentUsername + "' has been deleted.");
        currentUsername = null;
        currentPassword = null;
    }

    // Method to verify security question
    public boolean verifySecurityQuestion(String username) {
        System.out.print("Answer your security question (What is your favorite color?): ");
        String input = scanner.nextLine();
        return securityQuestions.containsKey(username) && securityQuestions.get(username).equals(input);
    }
}

