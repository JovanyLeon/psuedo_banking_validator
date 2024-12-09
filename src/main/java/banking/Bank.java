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
			String accountType = capitalizeFirstLetter(account.getType());
			String accountDetail = String.format("%s %s %.2f %.2f", accountType, account.getAccountId(),
					account.getBalance(), account.getApr());
			// Add current account state
			output.add(accountDetail);

			// Add transaction history for the account
			List<String> transactionHistory = commandStorage.getTransactionHistory(account.getAccountId());
			for (String transaction : transactionHistory) {
				output.add(capitalizeFirstLetter(transaction));
			}
		}
		return output;
	}

	private String capitalizeFirstLetter(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
}
