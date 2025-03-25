package bankapp;

public class BankAccount {

	private double balance;
	
	public BankAccount() {
		this.balance = 0;
	}
	
	public void deposit(double amount) {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}
		this.balance += amount;
	}
	
	public double getCurrentBalance() {
		return this.balance;
	}
		
	// Standard withdrawal method
    public void withdraw(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Withdrawal amount must be non-negative.");
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
        this.balance -= amount;
    }
    
    // Withdrawal with transaction description
    public void withdraw(double amount, String description) {
        if (amount < 0) {
            throw new IllegalArgumentException("Withdrawal amount must be non-negative.");
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
        System.out.println("Withdrawal Description: " + description);
        this.balance -= amount;
    }
    // Bulk withdrawal method (multiple withdrawals at once)
    public void withdraw(double[] amounts) {
        double totalWithdrawal = 0;
        for (double amount : amounts) {
            if (amount < 0) {
                throw new IllegalArgumentException("All withdrawal amounts must be non-negative.");
            }
            totalWithdrawal += amount;
        }
        if (totalWithdrawal > this.balance) {
            throw new IllegalArgumentException("Insufficient funds for bulk withdrawal.");
        }
        this.balance -= totalWithdrawal;
    }
}

