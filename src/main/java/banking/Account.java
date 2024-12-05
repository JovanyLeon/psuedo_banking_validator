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

	public void calculateMonthlyAPR() {
		if (balance > 0) {
			double monthlyRate = apr / 100 / 12; // Convert APR to monthly rate
			balance += balance * monthlyRate;
		}
	}

	public void pass(int months) {
		for (int i = 0; i < months; i++) {
			// Deduct minimum balance fee if applicable
			if (balance > 0 && balance < 100) {
				balance -= 25;
				if (balance < 0) {
					balance = 0;
				}
			}

			// Accrue monthly APR
			calculateMonthlyAPR();
		}
	}

}
