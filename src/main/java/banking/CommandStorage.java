package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandStorage {
	private List<String> invalidCommands;
	private Map<String, List<String>> accountTransactionHistory;

	public CommandStorage() {
		this.invalidCommands = new ArrayList<>();
		this.accountTransactionHistory = new HashMap<>();
	}

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public List<String> getInvalidCommands() {
		return new ArrayList<>(invalidCommands);
	}

	public void addTransaction(String accountId, String command) {
		accountTransactionHistory.computeIfAbsent(accountId, k -> new ArrayList<>()).add(command);
	}

	public List<String> getTransactionHistory(String accountId) {
		return accountTransactionHistory.getOrDefault(accountId, new ArrayList<>());
	}
}
