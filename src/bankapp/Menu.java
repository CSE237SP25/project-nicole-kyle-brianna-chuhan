package bankapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
	private static Map<String, BankAccount> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Bankapp system");
            System.out.println("1. create account");
            System.out.println("2. log in");
            System.out.println("3. exit");
            System.out.print("please choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Thanks for using");
                    return;
                default:
                    System.out.println("invalid input");
            }
        }
    }


    private static void createAccount() {
        System.out.print("create a password: ");
        String password = scanner.nextLine();
        BankAccount account = new BankAccount(password);
        accounts.put(String.valueOf(account.getAccountId()), account);
        
        System.out.println("Your account ID is: " + account.getAccountId());
    }
 
    private static void login() {
        System.out.print("enter ur ID: ");
        String accountId = scanner.nextLine();
        BankAccount account = accounts.get(accountId);

        if (account == null) {
            System.out.println("ur ID is invalde！");
            return;
        }

        System.out.print("password: ");
        String password = scanner.nextLine();

        if ((account.getCurrentBalance(password) == -1)) {
        	System.out.println("Your password is invalid！");
            return;
            
        }

        System.out.println("You have logged in！");
}
}
