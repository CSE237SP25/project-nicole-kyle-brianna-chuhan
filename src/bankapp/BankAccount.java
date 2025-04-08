package bankapp;
import java.util.ArrayList;
import java.util.List;
public class BankAccount {
	private double balance;
	private String accountType;
	private List<String> transactionHistory;
	private boolean isFrozen = false;
	
	public BankAccount(String accountType) {
		this.balance = 0;
		this.accountType = accountType;
		this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with type: " + accountType);
	}
	
	public void deposit(double amount) {
		if (isFrozen) throw new IllegalStateException("Account is frozen.");
		if(amount <= 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
		transactionHistory.add("Deposited: $" + String.format("%.2f", amount));
	}
	
	
   public void withdraw(double amount) {
	   if (isFrozen) throw new IllegalStateException("Account is frozen.");
       if (amount <= 0) {
           throw new IllegalArgumentException("Withdrawal amount must be non-negative.");
       }
       if (amount > this.balance) {
           throw new IllegalArgumentException("Insufficient funds.");
       }
       this.balance -= amount;
       transactionHistory.add("Withdrew: $" + String.format("%.2f", amount));
   }
  
	public double getCurrentBalance() {
		return this.balance;
	}
	
	public String getAccountType() {
		return accountType;
	}
	
    public void transferTo(BankAccount targetAccount, double amount) {
    	if (isFrozen) throw new IllegalStateException("Account is frozen.");
        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account cannot be null.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than 0.");
        }
        if (this.balance < amount) {
            throw new IllegalArgumentException("Insufficient funds for transfer.");
        }
        // Withdraw from the current account and deposit into the target account
        this.withdraw(amount);
        targetAccount.deposit(amount);
        transactionHistory.add("Transferred $" + String.format("%.2f", amount) + " to " + targetAccount.getAccountType() + " account.");
        targetAccount.addTransaction("Received $" + String.format("%.2f", amount) + " from another account.");
    }
    public void addTransaction(String message) {
        transactionHistory.add(message);
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
    public boolean isFrozen() {
        return isFrozen;
    }

    public void freezeAccount() {
        isFrozen = true;
        transactionHistory.add("Account has been frozen.");
    }

    public void unfreezeAccount() {
        isFrozen = false;
        transactionHistory.add("Account has been unfrozen.");
    }
	
}