package banking;

public class CommandProcessor {
	private final Bank bank;
	private final CommandValidator commandValidator;
	private final CommandStorage commandStorage;
	private final CreateCommandProcessor createCommandProcessor;
	private final DepositCommandProcessor depositCommandProcessor;
	private final PassCommandProcessor passCommandProcessor;
	private final WithdrawCommandProcessor withdrawCommandProcessor;
	private final TransferCommandProcessor transferCommandProcessor;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
		this.commandValidator = new CommandValidator(bank);
		this.commandStorage = new CommandStorage();
		this.createCommandProcessor = new CreateCommandProcessor(bank);
		this.depositCommandProcessor = new DepositCommandProcessor(bank);
		this.passCommandProcessor = new PassCommandProcessor(bank);
		this.withdrawCommandProcessor = new WithdrawCommandProcessor(bank);
		this.transferCommandProcessor = new TransferCommandProcessor(bank);
	}

	public void process(String command) {
		if (!commandValidator.validate(command)) {
			commandStorage.addInvalidCommand(command);
			return; // Store invalid commands
		}

		String[] parts = command.split(" ");
		String action = parts[0].toLowerCase();
		switch (action) {
		case "create":
			createCommandProcessor.process(command);
			break;
		case "deposit":
			depositCommandProcessor.process(command);
			commandStorage.addTransaction(parts[1], command); // Store deposit transaction
			break;
		case "withdraw":
			withdrawCommandProcessor.process(command);
			commandStorage.addTransaction(parts[1], command); // Store withdraw transaction
			break;
		case "transfer":
			transferCommandProcessor.process(command);
			commandStorage.addTransaction(parts[1], command); // Store transfer transaction for withdrawal
			commandStorage.addTransaction(parts[2], command); // Store transfer transaction for deposit
			break;
		case "pass":
			passCommandProcessor.process(command);
			break;
		default:
			commandStorage.addInvalidCommand(command); // Store unknown commands
		}
	}

	public CommandStorage getCommandStorage() {
		return commandStorage;
	}
}
