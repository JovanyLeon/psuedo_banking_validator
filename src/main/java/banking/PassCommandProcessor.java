package banking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PassCommandProcessor {
	private final Bank bank;
	private final PassCommandValidator validator;

	public PassCommandProcessor(Bank bank) {
		this.bank = bank;
		this.validator = new PassCommandValidator(bank);
	}

	public void process(String command) {
		if (!validator.validate(command)) {
			throw new IllegalArgumentException("Invalid pass command: " + command);
		}

		String[] parts = command.split(" ");
		int months = Integer.parseInt(parts[1]);
		bank.passMonths(months);

		for (int i = 0; i < months; i++) {
			// Collect keys of accounts to be closed
			List<String> accountsToClose = new ArrayList<>();

			Iterator<Map.Entry<String, Account>> iterator = bank.getAllAccounts().entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Account> entry = iterator.next();
				Account account = entry.getValue();

				if (account.getBalance() == 0) {
					accountsToClose.add(entry.getKey()); // Collect account to close
				} else {
					// Deduct the minimum balance fee first
					if (account.getBalance() < 100) {
						account.withdraw(25); // Deduct minimum balance fee
					}
					// Then calculate and accrue APR
					account.calculateMonthlyAPR();
				}
			}

			// Now close accounts after the iteration to avoid
			// ConcurrentModificationException
			for (String accountId : accountsToClose) {
				bank.closeAccount(accountId); // Close the account
			}
		}
	}
}
