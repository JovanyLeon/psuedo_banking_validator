public class CheckingCommandHandler {
	private Bank bank;

	public CheckingCommandHandler(Bank bank) {
		this.bank = bank;
	}

	public String processCreateCommand(String command) {
		String[] parts = command.split("\\s+");
		if (parts.length != 4) {
			return "Invalid arguments for create checking.";
		}

		try {
			String accountId = parts[2];
			double apr = Double.parseDouble(parts[3]);
			bank.createCheckingAccount(accountId, apr);
			return "Checking account created successfully.";
		} catch (NumberFormatException e) {
			return "Invalid number format.";
		}
	}
}