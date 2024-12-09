package banking;

public class WithdrawCommandProcessor {
	private final Bank bank;
	private final WithdrawCommandValidator validator;

	public WithdrawCommandProcessor(Bank bank) {
		this.validator = new WithdrawCommandValidator(bank);
		this.bank = bank;
	}

	public void process(String command) {
		if (!validator.validate(command)) {
			return; // Ignore invalid commands
		}

		String[] parts = command.split(" ");
		String accountId = parts[1];
		double amount = Double.parseDouble(parts[2]);
		Account account = bank.getAccount(accountId);

		if (amount >= account.getBalance()) {
			account.withdraw(account.getBalance()); // Withdraw the entire balance
		} else {
			account.withdraw(amount); // Withdraw the specified amount
		}
	}
}
