package bankapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user's bank account with basic operations:
 * deposit, withdraw, transfer, freeze, and transaction history tracking.
 */
public class BankAccount {
    private double balance;                   
    private String accountType;                
    private List<String> transactionHistory;   
    private boolean isFrozen = false;          

    /**
     * Constructor to initialize a bank account with the given type.
     * Starts with 0 balance.
     */
    public BankAccount(String accountType) {
        this.balance = 0;
        this.accountType = accountType;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with type: " + accountType);
    }

    /**
     * Deposits a positive amount into the account.
     * Throws if account is frozen or amount is invalid.
     */
    public void deposit(double amount) {
        if (isFrozen) throw new IllegalStateException("Account is frozen.");
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive.");

        this.balance += amount;
        transactionHistory.add("Deposited: $" + String.format("%.2f", amount));
    }

    /**
     * Withdraws a specified amount from the account.
     * If insufficient funds, allows overdraft and charges a fee.
     */
    public void withdraw(double amount) {
        if (isFrozen) throw new IllegalStateException("Account is frozen.");
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be non-negative.");

        if (amount > this.balance) {
            double overdraftFee = 10;
            System.out.println("Insufficient funds. You will be charged an overdraft fee of $" + overdraftFee);
            this.balance -= (overdraftFee);
            transactionHistory.add("Overdraft: Withdrew $" + String.format("%.2f", amount) + " + $" + overdraftFee + " fee");
        } else {
            this.balance -= amount;
            transactionHistory.add("Withdrew: $" + String.format("%.2f", amount));
        }
      
        // Alert if balance goes below $50
        if (this.balance < 50) {
            System.out.println("!Alert! Your account balance is below $50.");
        }
    }

 
    public double getCurrentBalance() {
        return this.balance;
    }

    public String getAccountType() {
        return accountType;
    }

    /**
     * Transfers a specified amount to another account.
     * Both withdrawal and deposit are logged.
     */
    public void transferTo(BankAccount targetAccount, double amount) {
        if (isFrozen) throw new IllegalStateException("Account is frozen.");
        if (targetAccount == null) throw new IllegalArgumentException("Target account cannot be null.");
        if (amount <= 0) throw new IllegalArgumentException("Transfer amount must be greater than 0.");
        if (this.balance < amount) throw new IllegalArgumentException("Insufficient funds for transfer.");

        this.withdraw(amount);
        targetAccount.deposit(amount);
        transactionHistory.add("Transferred $" + String.format("%.2f", amount) + " to " + targetAccount.getAccountType() + " account.");
        targetAccount.addTransaction("Received $" + String.format("%.2f", amount) + " from another account.");
    }

    /**
     * Adds a custom message to the transaction history.
     */
    public void addTransaction(String message) {
        transactionHistory.add(message);
    }

  
    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

   
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Freezes the account to disable transactions.
     */
    public void freezeAccount() {
        isFrozen = true;
        transactionHistory.add("Account has been frozen.");
    }

    /**
     * Unfreezes the account to allow transactions again.
     */
    public void unfreezeAccount() {
        isFrozen = false;
        transactionHistory.add("Account has been unfrozen.");
    }
}