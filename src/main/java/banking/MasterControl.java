package banking;

import java.util.ArrayList;
import java.util.List;

public class MasterControl {
	private final CommandProcessor commandProcessor;
	private final CommandStorage commandStorage;
	private final CommandValidator commandValidator;
	private final Bank bank;

	public MasterControl(Bank bank, CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage) {
		this.bank = bank;
		this.commandProcessor = commandProcessor;
		this.commandValidator = commandValidator;
		this.commandStorage = commandStorage;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			commandProcessor.process(command);
		}

		CommandStorage commandStorage = commandProcessor.getCommandStorage();

		List<String> output = new ArrayList<>();
		output.addAll(bank.generateAccountDetails(commandStorage)); // Account details + transaction history
		output.addAll(commandStorage.getInvalidCommands()); // Invalid commands
		return output;
	}
}
