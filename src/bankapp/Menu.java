package bankapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private static void withdrawFunds(BankAccount account, String password) {
        System.out.print("Enter amount to withdraw: $");
        try {
            double amount = scanner.nextDouble();
            scanner.nextLine();  // Clear the buffer

            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }

            // Call the withdraw method with a description
            account.withdraw(amount, description);
            double newBalance = account.getCurrentBalance(password);
            System.out.printf("Successfully withdrew $%.2f\n", amount);
            System.out.printf("New balance: $%.2f\n", newBalance);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();  // Clear invalid input
        }
    }
    
}