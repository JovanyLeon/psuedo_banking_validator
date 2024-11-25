public class CommandProcessor {
	private Bank bank;
	private CommandValidator validator;

	public CommandProcessor(Bank bank, CommandValidator validator) {
		this.bank = bank;
		this.validator = validator;
	}

	public String process(String command) {
		if (!validator.validate(command)) {
			return "Invalid command";
		}

		String[] parts = command.split("\\s+");
		String operation = parts[0];

		switch (operation.toLowerCase()) {
		case "create":
			return processCreate(parts);
		case "deposit":
			return processDeposit(parts);
		case "withdraw":
			return processWithdraw(parts);
		default:
			return "Unsupported operation";
		}
	}

	private String processCreate(String[] parts) {
		String accountType = parts[1].toLowerCase();
		String accountId = parts[2];
		double apr = Double.parseDouble(parts[3]);

		switch (accountType) {
		case "checking":
			bank.createCheckingAccount(accountId, apr);
			return "Checking account created";
		case "savings":
			bank.createSavingsAccount(accountId, apr);
			return "Savings account created";
		case "cd":
			double initialBalance = Double.parseDouble(parts[4]);
			bank.createCDAccount(accountId, apr, initialBalance);
			return "CD account created";
		default:
			return "Unsupported account type";
		}
	}

	private String processDeposit(String[] parts) {
		String accountId = parts[1];
		double amount = Double.parseDouble(parts[2]);
		Account account = bank.getAccount(accountId);

		if (account == null) {
			return "Account not found";
		}

		account.deposit(amount);
		return "Deposit successful";
	}

	private String processWithdraw(String[] parts) {
		String accountId = parts[1];
		double amount = Double.parseDouble(parts[2]);
		Account account = bank.getAccount(accountId);

		if (account == null) {
			return "Account not found";
		}

		account.withdraw(amount);
		return "Withdrawal successful";
	}
}
