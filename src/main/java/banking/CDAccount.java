package banking;

public class CDAccount extends Account {
	public CDAccount(String accountId, double apr, double initialBalance) {
		super(accountId, apr);
		setBalance(initialBalance);
	}

	@Override
	public void calculateMonthlyAPR() {
		double monthlyRate = getApr() / 12 / 100;
		for (int i = 0; i < 4; i++) { // Calculate APR 4 times per month
			double interest = getBalance() * monthlyRate;
			deposit(interest);
		}
	}

	@Override
	public String getType() {
		return "cd";
	}
}
