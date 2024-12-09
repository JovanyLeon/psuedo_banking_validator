package banking;

public class CreateCommandProcessor {
	private final Bank bank;
	private final CreateCommandValidator validator;

	public CreateCommandProcessor(Bank bank) {
		this.validator = new CreateCommandValidator(bank);
		this.bank = bank;
	}

	public void process(String command) {

		String[] parts = command.split(" ");
		String accountType = parts[1].toLowerCase();

		if (!accountType.equals("checking") && !accountType.equals("savings") && !accountType.equals("cd")) {
			return; // Invalid account type, do nothing
		}

		if (!validator.validate(command)) {
			return;
		}
		String accountId = parts[2];

		double apr = Double.parseDouble(parts[3]);

		switch (accountType) {
		case "checking":
			bank.createCheckingAccount(accountId, apr);
			break;
		case "savings":
			bank.createSavingsAccount(accountId, apr);
			break;
		case "cd":
			if (parts.length < 5) {
				throw new IllegalArgumentException("Invalid command format for CD account.");
			}
			double initialDeposit = Double.parseDouble(parts[4]);
			bank.createCDAccount(accountId, apr, initialDeposit);
			break;
		default:
			// Ignored since we already handle invalid types before
			break;
		}
	}

}
