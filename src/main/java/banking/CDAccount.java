package banking;

public class CDAccount extends Account {
	public CDAccount(String accountId, double apr, double initialBalance) {
		super(accountId, apr);
		setBalance(initialBalance);
	}

	@Override
	public String getType() {
		return "cd";
	}

}
