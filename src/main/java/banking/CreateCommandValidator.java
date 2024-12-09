package banking;

public class CreateCommandValidator {
	private final Bank bank;

	public CreateCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");

		if (parts.length < 4) {
			return false; // Not enough arguments
		}

		String accountType = parts[1].toLowerCase();
		String accountId = parts[2];

		// Validate argument count based on account type
		if (!hasValidArgumentCount(parts)) {
			return false; // Incorrect number of arguments
		}

		// Validate account type
		if (!isValidAccountType(accountType)) {
			return false; // Invalid account type
		}

		// Check if the account already exists
		if (bank.getAccount(accountId) != null) {
			return false; // Duplicate account ID
		}

		if (!isValidAccountId(accountId)) {
			return false; // Invalid account ID
		}

		// Validate APR
		if (!isValidAPR(parts[3])) {
			return false; // Invalid APR
		}

		// Additional validation for CD accounts (fifth argument is the initial deposit)
		if (accountType.equals("cd")) {
			if (!isValidInitialDeposit(parts[4])) {
				return false; // Invalid initial deposit
			}
		}

		return true;
	}

	private boolean hasValidArgumentCount(String[] parts) {
		String accountType = parts[1].toLowerCase();

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

	private boolean isValidAccountType(String accountType) {
		return accountType.equals("checking") || accountType.equals("savings") || accountType.equals("cd");
	}

	private boolean isValidAccountId(String accountId) {
		return accountId.matches("\\d{8}"); // Ensure ID is a unique 8-digit number
	}

	private boolean isValidAPR(String aprString) {
		try {
			double apr = Double.parseDouble(aprString);
			return apr >= 0 && apr <= 10; // APR must be a non-negative number and not exceed 10
		} catch (NumberFormatException e) {
			return false; // Invalid APR if it can't be parsed to a double
		}
	}

	private boolean isValidInitialDeposit(String depositString) {
		try {
			double deposit = Double.parseDouble(depositString);
			return deposit >= 1000 && deposit <= 10000; // Initial deposit must be between $1000 and $10000
		} catch (NumberFormatException e) {
			return false; // Invalid deposit if it can't be parsed to a double
		}
	}
}
