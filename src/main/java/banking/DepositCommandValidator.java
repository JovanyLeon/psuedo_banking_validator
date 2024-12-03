package banking;

public class DepositCommandValidator {
	private final Bank bank;

	public DepositCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String[] parts) {
		if (parts.length < 3) {
			return false; // Not enough arguments
		}

		String accountId = parts[1];

		// Check if the account exists
		Account account = bank.getAccount(accountId);
		if (account == null) {
			return false; // banking.Account does not exist
		}

		// Validate deposit amount
		try {
			double amount = Double.parseDouble(parts[2]);
			if (amount <= 0) {
				return false; // Deposit amount must be positive
			}
		} catch (NumberFormatException e) {
			return false; // Invalid deposit amount
		}

		return true;
	}
}