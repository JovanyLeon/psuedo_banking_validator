package banking;

public abstract class Account {

	private final String accountId;
	private final double apr; // Annual Percentage Rate
	private double balance;

	protected Account(String accountId, double apr) {
		this.accountId = accountId;
		this.apr = apr;
		this.balance = 0.0;
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
		if (amount > 0) {
			if (this.balance >= amount) {
				this.balance -= amount;
			} else {
				this.balance = 0;
			}
		}
		return amount;
	}

	public abstract String getType();
}
