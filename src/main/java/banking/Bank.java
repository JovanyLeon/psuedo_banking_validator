package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;

	public Bank() {
		this.accounts = new HashMap<>();
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

	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	public int getAccountsCount() {
		return accounts.size();
	}
}
