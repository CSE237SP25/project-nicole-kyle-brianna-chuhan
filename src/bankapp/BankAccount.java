package bankapp;
public class BankAccount {
	private double balance;
	private String accountType;
	
	public BankAccount(String accountType) {
		this.balance = 0;
		this.accountType = accountType;
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
        if (amount > this.balance) {
    	    System.out.println("Insufficient funds. You will be charged an overdraft fee of $" + overdraftFee);
            this.balance -= (amount + overdraftFee);
        } else {
        	this.balance -= amount;
        }
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
        // Withdraw from the current account and deposit into the target account
        this.withdraw(amount);
        targetAccount.deposit(amount);
    }
	
}
