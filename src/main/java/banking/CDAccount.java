package banking;

public class CDAccount extends Account {
	public CDAccount(String accountId, double apr, double initialBalance) {
		super(accountId, apr);
		setBalance(initialBalance);
	}

	@Override
	public void calculateMonthlyAPR() {
		if (getBalance() > 0) {
			double monthlyRate = getApr() / 100 / 12;
			for (int i = 0; i < 4; i++) { // Calculate APR 4 times per month
				double interest = getBalance() * monthlyRate;
				deposit(interest);
			}
		}
	}

	@Override
	public String getType() {
		return "cd";
	}

}
