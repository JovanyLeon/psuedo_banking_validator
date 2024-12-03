package banking;

public class CommandProcessor {
	private final Bank bank;
	private final CommandValidator commandValidator;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
		this.commandValidator = new CommandValidator(bank); // Internally initialize the validator
	}

	public void process(String command) {
		if (!commandValidator.validate(command)) {
			return; // Ignore invalid commands; they can be stored elsewhere
		}

		String[] parts = command.split(" ");
		switch (parts[0]) {
		case "create":
			processCreateCommand(parts);
			break;
		case "deposit":
			processDepositCommand(parts);
			break;
		default:
			throw new UnsupportedOperationException("Unknown command: " + parts[0]);
		}
	}

	private void processCreateCommand(String[] parts) {
		String accountType = parts[1];
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
			double initialDeposit = Double.parseDouble(parts[4]);
			bank.createCDAccount(accountId, apr, initialDeposit);
			break;
		default:
			throw new UnsupportedOperationException("Unknown account type: " + accountType);
		}
	}

	private void processDepositCommand(String[] parts) {
		String accountId = parts[1];
		double amount = Double.parseDouble(parts[2]);
		Account account = bank.getAccount(accountId);
		account.deposit(amount);
	}
}