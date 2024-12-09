package banking;

public class CommandValidator {
	private final Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		if (command == null || command.trim().isEmpty()) {
			return false; // Null or whitespace-only commands are invalid
		}

		String[] parts = command.trim().split("\\s+");
		String action = parts[0].toLowerCase();

		if (action.equals("create")) {
			return new CreateCommandValidator(bank).validate(command);
		} else if (action.equals("deposit")) {
			return new DepositCommandValidator(bank).validate(command);
		} else if (action.equals("pass")) {
			return new PassCommandValidator(bank).validate(command);
		} else if (action.equals("withdraw")) {
			return new WithdrawCommandValidator(bank).validate(command);
		} else if (action.equals("transfer")) {
			return new TransferCommandValidator(bank).validate(command);
		}
		return false; // Unsupported command type
	}
}