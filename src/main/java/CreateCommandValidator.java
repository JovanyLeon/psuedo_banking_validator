public class CreateCommandValidator {
	private final Bank bank;

	public CreateCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String[] parts) {
		if (parts.length < 4) {
			return false; // Not enough arguments
		}

		String accountType = parts[1];
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

	private boolean isValidInitialDeposit(String depositString) {
		try {
			double deposit = Double.parseDouble(depositString);
			return deposit > 0; // Initial deposit must be positive
		} catch (NumberFormatException e) {
			return false; // Invalid deposit if it can't be parsed to a double
		}
	}
}
