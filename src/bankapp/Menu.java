package bankapp;

public class Menu {
    
    private static void depositFunds(BankAccount account, String password) {
        System.out.print("Enter amount to deposit: $");
        try {
            double amount = scanner.nextDouble();
            scanner.nextLine();
            
            if (amount <= 0) {
                System.out.println("Amount must be greater than zero");
                return;
            }
            
            account.deposit(amount, password);
            double newBalance = account.getCurrentBalance(password);
            System.out.printf("Successfully deposited $%.2f\n", amount);
            System.out.printf("New balance: $%.2f\n", newBalance);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine(); // Clear invalid input
        }
    }
}
