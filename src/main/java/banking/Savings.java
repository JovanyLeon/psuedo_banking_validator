package banking;

public class Savings extends Account {
	public Savings(String accountId, double apr) {
		super(accountId, apr);
	}

	@Override
	public String getType() {
		return "savings";
	}
}
