public class CommandValidator {
	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validateCommand(String command) {
		String[] commandParts = command.split(" ");
		return false;
	}

	private boolean validateAccountCreation(String[] parts, int expectedParts) {
		if (parts.length != expectedParts) {
			return false;
		}
		String accountId = parts[1];
		if (bank.getAccount(accountId) != null) {
			return false;
		}
		try {
			Double.parseDouble(parts[2]); // APR
			if (expectedParts == 4)
				Double.parseDouble(parts[3]); // Initial balance for CD
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
