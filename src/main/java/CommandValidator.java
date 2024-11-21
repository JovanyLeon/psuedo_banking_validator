public class CommandValidator {
	private Bank bank;
	private CheckingCommandHandler checkingHandler;
	private SavingsCommandHandler savingsHandler;
	private CDCommandHandler cdHandler;

	public CommandValidator(Bank bank) {
		this.bank = bank;
		this.checkingHandler = new CheckingCommandHandler(bank);
		this.savingsHandler = new SavingsCommandHandler(bank);
		this.cdHandler = new CDCommandHandler(bank);
	}

	public String processCommand(String command) {
		if (command == null || command.trim().isEmpty()) {
			return "Invalid command format.";
		}

		String[] parts = command.trim().split("\\s+");
		if (parts.length < 2) {
			return "Invalid command format.";
		}

		String action = parts[0];
		String type = parts[1];

		switch (action) {
		case "create":
			return handleCreateCommand(parts);
		default:
			return "Unknown command.";
		}
	}

	private String handleCreateCommand(String[] parts) {
		if (parts.length < 3) {
			return "Invalid create command format.";
		}

		String type = parts[1];
		switch (type) {
		case "checking":
			if (parts.length == 4) {
				return new CheckingCommandHandler(bank).processCreateCommand(String.join(" ", parts));
			} else {
				return "Invalid arguments for create checking.";
			}
		case "savings":
			if (parts.length == 4) {
				return new SavingsCommandHandler(bank).processCreateCommand(String.join(" ", parts));
			} else {
				return "Invalid arguments for create savings.";
			}
		case "cd":
			if (parts.length == 5) {
				return new CDCommandHandler(bank).processCreateCommand(String.join(" ", parts));
			} else {
				return "Invalid arguments for create cd.";
			}
		default:
			return "Unknown account type.";
		}
	}

}
