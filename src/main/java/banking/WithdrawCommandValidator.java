package banking;

public class WithdrawCommandValidator {
	private final Bank bank;

	public WithdrawCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");

		if (parts.length < 3) {
			return false; // Not enough arguments
		}

		String accountId = parts[1];
		double amount;

		// Validate withdrawal amount
		try {
			amount = Double.parseDouble(parts[2]);
			if (amount < 0) {
				return false; // Withdraw amount cannot be negative
			}
		} catch (NumberFormatException e) {
			return false; // Invalid withdrawal amount
		}

		// Check if the account exists
		Account account = bank.getAccount(accountId);
		if (account == null) {
			return false; // Account does not exist
		}

		// Ensure the withdrawal follows the account-specific rules
		String accountType = account.getType();
		if (accountType.equals("checking")) {
			if (amount > 400) {
				return false; // Withdrawal amount exceeds the maximum for checking accounts
			}
		} else if (accountType.equals("savings")) {
			if (amount > 1000) {
				return false; // Withdrawal amount exceeds the maximum for savings accounts
			}
			if (account.getWithdrawalsThisMonth() >= 1) {
				return false; // Savings accounts have a maximum of 1 withdrawal per month
			}
		} else if (accountType.equals("cd")) {
			if (account.getMonthsPassed() < 12) {
				return false; // CDs cannot be withdrawn from until 12 months have passed
			}
			if (amount < account.getBalance() || amount == 0) {
				return false; // Only full withdrawal is allowed for CDs
			}
		}

		return true;
	}
}
