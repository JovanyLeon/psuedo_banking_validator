package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;

	public Bank() {
		this.accounts = new HashMap<>();
	}

	public void closeAccount(String accountId) {
		if (!accounts.containsKey(accountId)) {
			return;
		}
		accounts.remove(accountId);
	}

	public void createCheckingAccount(String accountId, double apr) {
		accounts.put(accountId, new Checkings(accountId, apr));
	}

	public void createSavingsAccount(String accountId, double apr) {
		accounts.put(accountId, new Savings(accountId, apr));
	}

	public void createCDAccount(String accountId, double apr, double initialBalance) {
		accounts.put(accountId, new CDAccount(accountId, apr, initialBalance));
	}

	public Map<String, Account> getAllAccounts() {
		return accounts;
	}

	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	public int getAccountsCount() {
		return accounts.size();
	}

	public void passMonths(int months) {
		for (Account account : accounts.values()) {
			for (int i = 0; i < months; i++) {
				account.passMonth();
			}
		}
	}

	public List<String> generateAccountDetails(CommandStorage commandStorage) {
		List<String> output = new ArrayList<>();
		for (Account account : accounts.values()) {
			// Add current account state
			output.add(account.getFormattedDetails());

			// Add transaction history for the account
			List<String> transactionHistory = commandStorage.getTransactionHistory(account.getAccountId());
			output.addAll(transactionHistory);
		}
		return output;
	}
}
