package banking;

public class DepositCommandProcessor {
	private final Bank bank;
	private final DepositCommandValidator validator;

	public DepositCommandProcessor(Bank bank) {
		this.validator = new DepositCommandValidator(bank);
		this.bank = bank;
	}

	public void process(String command) {
		if (!validator.validate(command)) {
			return;
		}

		String[] parts = command.split(" ");
		String action = parts[0].toLowerCase();
		String accountId = parts[1];
		double amount = Double.parseDouble(parts[2]);
		Account account = bank.getAccount(accountId);
		account.deposit(amount);
	}
}
