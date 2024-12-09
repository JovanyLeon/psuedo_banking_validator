package banking;

public class TransferCommandValidator {
	private final Bank bank;

	public TransferCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");
		if (parts.length < 4) {
			return false; // Not enough arguments
		}

		String fromAccountId = parts[1];
		String toAccountId = parts[2];
		double amount;

		// Validate transfer amount
		try {
			amount = Double.parseDouble(parts[3]);
			if (amount < 0) {
				return false; // Transfer amount cannot be negative
			}
		} catch (NumberFormatException e) {
			return false; // Invalid transfer amount
		}

		// Check if both accounts exist and are valid
		Account fromAccount = bank.getAccount(fromAccountId);
		Account toAccount = bank.getAccount(toAccountId);

		if (fromAccount == null || toAccount == null) {
			return false; // One or both accounts do not exist
		}

		// Check that neither account is a CD account
		if (fromAccount.getType().equals("cd") || toAccount.getType().equals("cd")) {
			return false; // CD accounts cannot be part of a transfer
		}

		// Validate transfer between allowable account types
		if (!((fromAccount.getType().equals("checking") || fromAccount.getType().equals("savings"))
				&& (toAccount.getType().equals("checking") || toAccount.getType().equals("savings")))) {
			return false; // Transfer must be between checking or savings accounts only
		}

		// All other withdrawal and deposit rules will be applied by respective
		// processors

		return true;
	}
}
