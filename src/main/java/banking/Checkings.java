package banking;

public class Checkings extends Account {
	public Checkings(String accountId, double apr) {
		super(accountId, apr);
	}

	@Override
	public String getType() {
		return "checking";
	}

}
