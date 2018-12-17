package ATMWithJava;

import java.sql.Date;

public class Transaction {

	private int accountNumber;
	private Date date;
	private String type;
	private int amount;
	private double balance;

	public Transaction(int accountNumber, Date date, String type, int amount,
			double balance) {
		this.accountNumber = accountNumber;
		this.date = date;
		this.type = type;
		this.amount = amount;
		this.balance = balance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
