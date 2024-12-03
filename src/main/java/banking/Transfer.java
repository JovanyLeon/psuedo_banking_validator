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

		fromAccount.withdraw(amount);
		toAccount.deposit(amount);
	}
}
