package banking;

import java.text.DecimalFormat;

public abstract class Account {

	private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

	static {
		decimalFormat.setRoundingMode(java.math.RoundingMode.FLOOR);
	}

	private final String accountId;
	private final double apr; // Annual Percentage Rate
	private double balance;
	private int withdrawalsThisMonth;
	private int monthsPassed;

	protected Account(String accountId, double apr) {
		this.accountId = accountId;
		this.apr = apr;
		this.balance = 0.0;
		this.withdrawalsThisMonth = 0;
	}

	public String getAccountId() {
		return accountId;
	}

	public double getApr() {
		return apr;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void deposit(double amount) {
		if (amount > 0) {
			this.balance += amount;
		}
	}

	public double withdraw(double amount) {
		withdrawalsThisMonth++;
		if (this.balance >= amount) {
			this.balance -= amount;
		} else {
			this.balance = 0;
		}
		return amount;
	}

	public double transfer(double amount) {
		double actualWithdraw = Math.min(amount, balance);
		this.balance -= actualWithdraw;
		if (balance < 0) {
			this.balance = 0;
		}
		return actualWithdraw;
	}

	public void passMonth() {
		monthsPassed++; // Increment months passed
		withdrawalsThisMonth = 0; // Reset withdrawals for the new month
	}

	public int getMonthsPassed() {
		return monthsPassed;
	}

	public int getWithdrawalsThisMonth() {
		return withdrawalsThisMonth;
	}

	public abstract String getType();

	public void calculateMonthlyAPR() {
		double monthlyRate = apr / 100 / 12;
		double interest = balance * monthlyRate;
		balance += interest;

		setBalance(Math.round(getBalance() * 100.0) / 100.0);
	}

}
