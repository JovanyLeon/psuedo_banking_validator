package banking;

public class DepositCommandValidator {
	private final Bank bank;

	public DepositCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");

		if (parts.length < 3) {
			return false; // Not enough arguments
		}

		String accountId = parts[1];
		double amount;

		// Validate deposit amount
		try {
			amount = Double.parseDouble(parts[2]);
			if (amount < 0) {
				return false; // Deposit amount cannot be negative
			}
		} catch (NumberFormatException e) {
			return false; // Invalid deposit amount
		}

		// Check if the account exists
		Account account = bank.getAccount(accountId);
		if (account == null) {
			return false; // Account does not exist
		}

		// Ensure the deposit follows the account-specific rules
		String accountType = account.getType();
		if (accountType.equals("checking")) {
			if (amount > 1000) {
				return false; // Deposit amount exceeds the maximum for checking accounts
			}
		} else if (accountType.equals("savings")) {
			if (amount > 2500) {
				return false; // Deposit amount exceeds the maximum for savings accounts
			}
		} else if (accountType.equals("cd")) {
			return false; // CDs cannot be deposited into
		}

		return true;
	}
}
