package banking;

public class Transfer {
	private final Bank bank;

	public Transfer(Bank bank) {
		this.bank = bank;
	}

	public void transfer(String fromAccountId, String toAccountId, double amount) {
		if (amount <= 0) {
			return;
		}

		Account fromAccount = bank.getAccount(fromAccountId);
		Account toAccount = bank.getAccount(toAccountId);

		if (fromAccount == null) {
			return;
		}

		if (toAccount == null) {
			return;
		}

		String fromAccountType = bank.getAccountType(fromAccountId);
		String toAccountType = bank.getAccountType(toAccountId);

		if (fromAccountType.equals("cd") || toAccountType.equals("cd")) {
			throw new IllegalArgumentException("CD accounts cannot be involved in a transfer.");
		}

		fromAccount.withdraw(amount);
		toAccount.deposit(amount);
	}
}
