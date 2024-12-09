package banking;

public class TransferCommandProcessor {
	private final Bank bank;
	private final TransferCommandValidator validator;

	public TransferCommandProcessor(Bank bank) {
		this.validator = new TransferCommandValidator(bank);
		this.bank = bank;
	}

	public void process(String command) {
		if (!validator.validate(command)) {
			return; // Ignore invalid commands
		}

		String[] parts = command.split(" ");
		String fromAccountId = parts[1];
		String toAccountId = parts[2];
		double amount = Double.parseDouble(parts[3]);

		Account fromAccount = bank.getAccount(fromAccountId);
		Account toAccount = bank.getAccount(toAccountId);

		// Transfer the amount from the fromAccount
		double transferredAmount = fromAccount.transfer(amount);

		// Deposit the transferred amount into the toAccount
		toAccount.deposit(transferredAmount);
	}
}
