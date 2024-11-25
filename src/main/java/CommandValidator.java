public class CommandValidator {

	private final Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		if (command == null || command.trim().isEmpty()) {
			return false; // Invalid input
		}

		String[] parts = command.trim().split("\\s+");
		if (parts.length < 2) {
			return false; // Too few arguments
		}

		String action = parts[0];
		switch (action) {
		case "create":
			return isValidCreateCommand(parts);
		case "deposit":
			return isValidDepositCommand(parts);
		default:
			return false; // Unknown command
		}
	}

	private boolean isValidCreateCommand(String[] parts) {
		if (parts.length < 4) {
			return false; // Not enough arguments
		}

		String accountType = parts[1];
		String accountId = parts[2];
		if (bank.getAccount(accountId) != null) {
			return false; // Duplicate account ID
		}

		try {
			double apr = Double.parseDouble(parts[3]);
			if (apr < 0) {
				return false; // APR must be non-negative
			}

			if (accountType.equals("cd") && parts.length == 5) {
				double initialBalance = Double.parseDouble(parts[4]);
				return initialBalance >= 0; // Initial balance must be non-negative
			}

			return accountType.equals("checking") || accountType.equals("savings");
		} catch (NumberFormatException e) {
			return false; // Invalid APR or initial balance format
		}
	}

	private boolean isValidDepositCommand(String[] parts) {
		if (parts.length != 3) {
			return false; // Deposit commands must have exactly 3 parts
		}

		String accountId = parts[1];
		try {
			double amount = Double.parseDouble(parts[2]);
			return bank.getAccount(accountId) != null && amount > 0; // Account must exist, and amount must be positive
		} catch (NumberFormatException e) {
			return false; // Invalid amount format
		}
	}
}