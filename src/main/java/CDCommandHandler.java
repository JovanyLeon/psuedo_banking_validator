public class CDCommandHandler {
	private Bank bank;

	public CDCommandHandler(Bank bank) {
		this.bank = bank;
	}

	public String processCreateCommand(String command) {
		String[] parts = command.split("\\s+");
		if (parts.length != 5) {
			return "Invalid arguments for create cd.";
		}

		try {
			String accountId = parts[2];
			double apr = Double.parseDouble(parts[3]);
			double initialBalance = Double.parseDouble(parts[4]);
			bank.createCDAccount(accountId, apr, initialBalance);
			return "CD account created successfully.";
		} catch (NumberFormatException e) {
			return "Invalid number format.";
		}
	}
}