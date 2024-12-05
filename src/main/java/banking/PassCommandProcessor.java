package banking;

import java.util.Iterator;
import java.util.Map;

public class PassCommandProcessor {
	private final Bank bank;
	private final PassCommandValidator validator;

	public PassCommandProcessor(Bank bank, PassCommandValidator validator) {
		this.bank = bank;
		this.validator = validator;
	}

	public void process(String command) {
		if (!validator.validate(command)) {
			throw new IllegalArgumentException("Invalid pass command: " + command);
		}

		String[] parts = command.split(" ");
		int months = Integer.parseInt(parts[1]);

		for (int i = 0; i < months; i++) {
			Iterator<Map.Entry<String, Account>> iterator = bank.getAllAccounts().entrySet().iterator();

			while (iterator.hasNext()) {
				Map.Entry<String, Account> entry = iterator.next();
				Account account = entry.getValue();

				if (account.getBalance() == 0) {
					iterator.remove(); // Close account if balance is zero
				} else {
					if (account.getBalance() < 100) {
						account.withdraw(25); // Deduct minimum balance fee
					}
					account.calculateMonthlyAPR(); // Apply monthly APR calculation
				}
			}
		}
	}
}
