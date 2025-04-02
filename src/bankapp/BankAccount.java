package bankapp;

public class BankAccount {

	private double balance;
	private String accountType;

	
	public BankAccount() {
		this.balance = 0;
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
	
}