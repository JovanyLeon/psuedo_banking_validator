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

		// Validate argument count
		if (!hasValidArgumentCount(parts)) {
			return false; // Invalid number of arguments for the create command
		}

		String accountType = parts[1];
		String accountId = parts[2];

		// Validate account type
		if (!isValidAccountType(accountType)) {
			return false; // Invalid account type
		}

		// Check if the account already exists
		if (bank.getAccount(accountId) != null) {
			return false; // Duplicate account ID
		}

		// Validate APR
		return isValidAPR(parts[3]); // Invalid APR
	}

	private boolean isValidAccountType(String accountType) {
		return accountType.equals("checking") || accountType.equals("savings") || accountType.equals("cd");
	}

	private boolean isValidAPR(String aprString) {
		try {
			double apr = Double.parseDouble(aprString);
			return apr >= 0; // APR must be a non-negative number
		} catch (NumberFormatException e) {
			return false; // Invalid APR if it can't be parsed to a double
		}
	}

	private boolean hasValidArgumentCount(String[] parts) {
		String accountType = parts[1];

		// If creating a checking or savings account, it should have exactly 4 arguments
		if (accountType.equals("checking") || accountType.equals("savings")) {
			return parts.length == 4;
		}

		// If creating a CD account, it should have exactly 5 arguments
		if (accountType.equals("cd")) {
			return parts.length == 5;
		}

		// If the account type is invalid, return false
		return false;
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