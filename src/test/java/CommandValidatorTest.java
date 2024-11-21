import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {

	private Bank bank;
	private CommandValidator validator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validator = new CommandValidator(bank);
	}

	@Test
	public void testCreateChecking() {
		String result = validator.processCommand("create checking A1 0.01");
		assertEquals("Checking account created successfully.", result);
		Account account = bank.getAccount("A1");
		assertNotNull(account);
		assertEquals("A1", account.getAccountId());
		assertEquals(0.01, account.getApr(), 0.01);
	}

	@Test
	public void testCreateSavings() {
		String result = validator.processCommand("create savings A2 0.02");
		assertEquals("Savings account created successfully.", result);
		Account account = bank.getAccount("A2");
		assertNotNull(account);
		assertEquals("A2", account.getAccountId());
		assertEquals(0.02, account.getApr(), 0.01);
	}

	@Test
	public void testCreateCD() {
		String result = validator.processCommand("create cd A3 0.03 1000");
		assertEquals("CD account created successfully.", result);
		Account account = bank.getAccount("A3");
		assertNotNull(account);
		assertEquals("A3", account.getAccountId());
		assertEquals(0.03, account.getApr(), 0.01);
		assertEquals(1000, account.getBalance(), 0.01);
	}

	@Test
	public void testAccountsCount() {
		validator.processCommand("create checking A1 0.01");
		validator.processCommand("create savings A2 0.02");
		validator.processCommand("create cd A3 0.03 1000");

		String result = validator.processCommand("accounts_count");
		assertEquals("Total accounts: 3", result);
	}

	@Test
	public void testInvalidCreateCommand() {
		String result = validator.processCommand("create");
		assertEquals("Invalid create command format.", result);

		result = validator.processCommand("create invalid A1 0.01");
		assertEquals("Unknown account type.", result);

		result = validator.processCommand("create checking");
		assertEquals("Invalid arguments for create checking.", result);
	}

	@Test
	public void testUnknownCommand() {
		String result = validator.processCommand("unknown_command");
		assertEquals("Unknown command.", result);
	}

	@Test
	public void testEmptyCommand() {
		String result = validator.processCommand("");
		assertEquals("Invalid command format.", result);
	}
}