public class SavingsCommandHandler {
	private Bank bank;

	public SavingsCommandHandler(Bank bank) {
		this.bank = bank;
	}

	public String processCreateCommand(String command) {
		String[] parts = command.split("\\s+");
		if (parts.length != 4) {
			return "Invalid arguments for create savings.";
		}

		try {
			String accountId = parts[2];
			double apr = Double.parseDouble(parts[3]);
			bank.createSavingsAccount(accountId, apr);
			return "Savings account created successfully.";
		} catch (NumberFormatException e) {
			return "Invalid number format.";
		}
	}
}
