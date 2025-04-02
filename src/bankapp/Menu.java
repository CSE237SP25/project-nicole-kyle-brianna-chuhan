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
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
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
                    System.out.println("Logged out.");
                    currentAccount = null;
                    return;
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
        scanner.nextLine(); // consume newline
        return value;
    }
}


