package bankapp;

import java.util.UUID;

public class BankAccount {
	private static int nextId = 1000;
    private int accountId;
    private String password;
    private double balance;

 
    public BankAccount(String password) {
    	this.accountId = nextId++;
        this.password = password;
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("You must deposit more than 0");
        }
        this.balance += amount;
        System.out.println("current balance：" + this.balance);
    }
	
    public double getCurrentBalance(String inputPassword) {
        if (authenticate(inputPassword)) {
            return this.balance;
        }
        System.out.println("invalid password");
        return -1;
    }
    private boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

  
    public int getAccountId() {
        return accountId;
    }
}
