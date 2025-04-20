package bankapp;
public class BankAccount {
	private double balance;
	private String accountType;

	private Double spendingLimit;
	
	public BankAccount(String accountType) {
		this.balance = 0;
		this.accountType = accountType;
		this.spendingLimit = null;

	}
	
	public void deposit(double amount) {
		if(amount <= 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
	}
	
	
   public void withdraw(double amount) {
       if (amount <= 0) {
           throw new IllegalArgumentException("Withdrawal amount must be non-negative.");
       }
       if (spendingLimit != null && amount > spendingLimit) {
           throw new IllegalArgumentException("Cannot withdraw more than your spending limit: " + spendingLimit);
       }
       if (amount > this.balance) {
           throw new IllegalArgumentException("Insufficient funds.");
       }
       this.balance -= amount;
   }
  
	public double getCurrentBalance() {
		return this.balance;
	}
	
	public String getAccountType() {
		return accountType;
	}
	
    public void transferTo(BankAccount targetAccount, double amount) {
        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account cannot be null.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than 0.");
        }
        if (this.balance < amount) {
            throw new IllegalArgumentException("Insufficient funds for transfer.");
        }

	if (spendingLimit != null && amount > spendingLimit) {
            throw new IllegalArgumentException("Cannot transfer more than your spending limit: " + spendingLimit);
        }
	
        // Withdraw from the current account and deposit into the target account
        this.withdraw(amount);
        targetAccount.deposit(amount);
    }
	
    public void setSpendingLimit(double limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Spending limit must be positive.");
        }
        this.spendingLimit = limit;
    }

    public void clearSpendingLimit() {
        this.spendingLimit = null;
    }

    public Double getSpendingLimit() {
        return this.spendingLimit;
    }
}
