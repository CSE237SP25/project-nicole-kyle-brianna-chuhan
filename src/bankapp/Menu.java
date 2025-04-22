package bankapp;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Main application menu for BankApp.
 * Handles user navigation, account access, and operations.
 */
public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static HashMap<String, String> userDatabase = new HashMap<>();
    private static BankAccount currentAccount = null;
    private static CreateAccount accountManager;

    public static void main(String[] args) {
        accountManager = new CreateAccount(userDatabase, scanner);

        while (true) {
            System.out.println("\n--- Welcome to BankApp ---");
            System.out.println("1. Log in / Create Account");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    currentAccount = accountManager.authenticateUser();
                    if (currentAccount != null) {
                        showAccountMenu();
                    }
                    break;
                case "2":
                    System.out.println("Thanks for using BankApp!");
                    return;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }
    

    private static void showAccountMenu() {
        while (true) {
            System.out.println("\n--- Account Menu (" + currentAccount.getAccountType() + ") ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Change Password");
            System.out.println("5. Transfer Money");
            System.out.println("6. Account Management");

          String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (currentAccount.isFrozen()) {
                        System.out.println("Account is frozen. Please unfreeze it to make a deposit.");
                        break;
                    }
                    System.out.print("Enter amount to deposit (or type 'back' to return): ");
                    String depositInput = scanner.nextLine();
                    if (checkBack(depositInput)) break;
                    double depositAmount = parseDoubleOrBack(depositInput);
                    if (depositAmount == -1) break;
                    try {
                        currentAccount.deposit(depositAmount);
                        System.out.println("Deposit successful.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid deposit amount.");
                    }
                    break;

                case "2":
                    if (currentAccount.isFrozen()) {
                        System.out.println("Account is frozen. Please unfreeze it to make a withdrawal.");
                        break;
                    }
                    System.out.print("Enter amount to withdraw (or type 'back' to return): ");
                    String withdrawInput = scanner.nextLine();
                    if (checkBack(withdrawInput)) break;
                    double withdrawAmount = parseDoubleOrBack(withdrawInput);
                    if (withdrawAmount == -1) break;

                    if (withdrawAmount >= 10000) { // Verifying security question for large withdrawals

                        if (!accountManager.verifySecurityQuestion(accountManager.getCurrentUsername())) {
                            System.out.println("Security question incorrect. Logging out.");
                            return;
                        }
                    }
                    try {
                        currentAccount.withdraw(withdrawAmount);
                        System.out.println("Withdrawal successful.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "3":
                    System.out.printf("Current balance: $%.2f\n", currentAccount.getCurrentBalance());
                    break;

                case "4":
                    accountManager.changePassword();
                    break;

                case "5":
                    if (currentAccount.isFrozen()) {
                        System.out.println("Account is frozen. Please unfreeze it to transfer.");
                        break;
                    }
                    System.out.print("Enter recipient username (or type 'back' to return): ");
                    String recipientUsername = scanner.nextLine();
                    if (checkBack(recipientUsername)) break;
                    BankAccount recipientAccount = accountManager.getAccountByUsername(recipientUsername);
                    if (recipientAccount == null) {
                        System.out.println("Recipient account not found.");
                        break;
                    }
                    System.out.print("Enter amount to transfer (or type 'back' to return): ");
                    String transferInput = scanner.nextLine();
                    if (checkBack(transferInput)) break;
                    double transferAmount = parseDoubleOrBack(transferInput);
                    if (transferAmount == -1) break;
                
                    if (transferAmount >= 10000) { // Verifying security question for large transfers
                        if (!accountManager.verifySecurityQuestion(accountManager.getCurrentUsername())) {
                            System.out.println("Security question incorrect. Logging out.");
                            return;
                        }
                    }
                    try {
                        currentAccount.transferTo(recipientAccount, transferAmount);
                        System.out.println("Transfer successful.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "6":
                    showAccountManagementMenu(); 
                    if (currentAccount == null) return; 
                    break;

                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    private static void showAccountManagementMenu() {
        while (true) {
            System.out.println("\n--- Account Management Menu ---");
            System.out.println("1. View Transaction History");
            System.out.println("2. Freeze/Unfreeze Account");
            System.out.println("3. Delete Account");
            System.out.println("4. Logout");
            System.out.println("5. View Account Info");
            System.out.println("6. Set Spending Limit");
            System.out.println("7. Back to Account Menu");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("--- Transaction History ---");
                    for (String record : currentAccount.getTransactionHistory()) {
                        System.out.println(record);
                    }
                    break;
                
                case "2":
                    if (currentAccount.isFrozen()) {
                        System.out.print("Account is currently frozen. Unfreeze it? (yes/no, or type 'back' to return): ");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (checkBack(input)) break;
                        if (input.equals("yes")) {
                            if (!accountManager.verifySecurityQuestion(accountManager.getCurrentUsername())) { // Verifying security question before unfreezing
                                System.out.println("Security question incorrect. Logging out.");
                                return;
                            }
                            currentAccount.unfreezeAccount();
                            System.out.println("Account has been unfrozen.");
                        } else {
                            System.out.println("No changes made.");
                        }
                    } else {
                        System.out.print("Are you sure you want to freeze your account? (yes/no, or type 'back' to return): ");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (checkBack(input)) break;
                        if (input.equals("yes")) {
                            currentAccount.freezeAccount();
                            System.out.println("Account has been frozen.");
                        } else {
                            System.out.println("No changes made.");
                        }
                    }
                    break;

                case "3":
                    accountManager.deleteAccount();
                    currentAccount = null;
                    return; 

                case "4":
                    System.out.println("Logged out.");
                    currentAccount = null;
                    return; 
                    
                case "5": 
                    viewAccountInfo(); 
                    break;

                case "6":
                    setSpendingLimitMenuOption();
                    break;
                    
                case "7":
                    return; 

                default:
                    System.out.println("Invalid input.");
            }

            if (currentAccount == null) {
               
                return;
            }
        }
    }
    private static void viewAccountInfo() {
        System.out.println("\n--- Account Information ---");
        System.out.println("Username: " + accountManager.getCurrentUsername());  
        System.out.println("Account Type: " + currentAccount.getAccountType());
        System.out.println("Current Balance: $" + String.format("%.2f", currentAccount.getCurrentBalance()));
        System.out.println("\n--- Deposit, Withdrawal, and Transfer Records ---");

        boolean hasRecord = false;
        for (String record : currentAccount.getTransactionHistory()) {
            String lowerRecord = record.toLowerCase();
            if (lowerRecord.contains("deposit") || lowerRecord.contains("withdrew") || lowerRecord.contains("transfer")) {
                System.out.println(record);
                hasRecord = true;
            }
        }

        if (!hasRecord) {
            System.out.println("No deposit, withdrawal, or transfer records found.");
        }
    }
    // Spending Limit
    private static void setSpendingLimitMenuOption() {
      System.out.print("Enter a new spending limit (e.g. '100'), 'clear' to remove the limit, or 'back' to return: ");
      String limitInput = scanner.nextLine().trim();

      // Check if user wants to go back
      if (checkBack(limitInput)) {
          return;
      }
    
      // If user wants to clear the limit:
      if (limitInput.equalsIgnoreCase("clear")) {
          currentAccount.clearSpendingLimit();
          System.out.println("Spending limit cleared.");
          return;
      }

      // Otherwise, try to parse a double
      double parsedLimit = parseDoubleOrBack(limitInput);
      if (parsedLimit == -1) {
          // parseDoubleOrBack() will already show an error or handle 'back'
          return;
      }

          // Attempt to set the spending limit
      try {
          currentAccount.setSpendingLimit(parsedLimit);
          System.out.println("Spending limit set to: $" + parsedLimit);
      } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
      }
    }           

    // Check for 'back' input
    private static boolean checkBack(String input) {
        return input.trim().equalsIgnoreCase("back");
    }

    // Parse double or return -1 if 'back'
    private static double parseDoubleOrBack(String input) {
        if (checkBack(input)) return -1;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return -1;
        }
    }
}

