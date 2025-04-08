package bankapp;


import java.util.HashMap;
import java.util.Scanner;


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
            System.out.println("6. Logout");
            System.out.println("7. View Transaction History");
            System.out.println("8.Freeze account: ");
            String choice = scanner.nextLine();


            switch (choice) {
                case "1":
                    if (currentAccount.isFrozen()) {
                        System.out.println("Account is frozen. Please unfreeze it to make a deposit.");
                        break;
                    }
                    System.out.print("Enter amount to deposit: ");
                    double dep = getDoubleInput();
                    try {
                        currentAccount.deposit(dep);
                        System.out.println("Deposit successful.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid deposit amount.");
                    }
                    break;
                case "2":
                	if (currentAccount.isFrozen()) {
                        System.out.println("Account is frozen. Please unfreeze it to make a deposit.");
                        break;
                    }
                    System.out.print("Enter amount to withdraw: ");
                    double wd = getDoubleInput();
                    try {
                        currentAccount.withdraw(wd);
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
                        System.out.println("Account is frozen. Please unfreeze it to make a deposit.");
                        break;
                    }
                    System.out.print("Enter recipient username: ");
                    String recipientUsername = scanner.nextLine(); 
                    
                    BankAccount recipientAccount = accountManager.getAccountByUsername(recipientUsername);
                    if (recipientAccount == null) { 
                    	System.out.println("Recipient account not found."); 
                        break;
                    }

                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = getDoubleInput();

                    try {
                        currentAccount.transferTo(recipientAccount, transferAmount);
                        System.out.println("Transfer successful.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "6":
                	
                	
                    System.out.println("Logged out.");
                    currentAccount = null;
                    return;
                case "7":
                    System.out.println("--- Transaction History ---");
                    for (String record : currentAccount.getTransactionHistory()) {
                        System.out.println(record);
                    }
                    break;
                case "8":
                    if (currentAccount.isFrozen()) {
                        System.out.print("Account is currently frozen. Do you want to unfreeze it? (yes/no): ");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("yes")) {
                            currentAccount.unfreezeAccount();
                            System.out.println("Account has been unfrozen.");
                        } else {
                            System.out.println("No changes made.");
                        }
                    } else {
                        System.out.print("Are you sure you want to freeze your account? (yes/no): ");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("yes")) {
                            currentAccount.freezeAccount();
                            System.out.println("Account has been frozen.");
                        } else {
                            System.out.println("No changes made.");
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }


    private static double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); 
        return value;
    }
}
