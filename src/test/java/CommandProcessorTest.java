import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	private Bank bank;
	private CommandProcessor processor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		processor = new CommandProcessor(bank);
	}

	// Valid Command Tests
	@Test
	public void testProcessValidCreateCheckingCommand() {
		processor.process("create checking 12345678 1.0");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Bank should contain the created checking account.");
		assertEquals(1.0, account.getApr(), "Account APR should match.");
	}

	@Test
	public void testProcessValidCreateSavingsCommand() {
		processor.process("create savings 87654321 2.5");

		Account account = bank.getAccount("87654321");
		assertNotNull(account, "Bank should contain the created savings account.");
		assertEquals(2.5, account.getApr(), "Account APR should match.");
	}

	@Test
	public void testProcessValidCreateCDCommand() {
		processor.process("create cd 11223344 1.5 1000");

		Account account = bank.getAccount("11223344");
		assertNotNull(account, "Bank should contain the created CD account.");
		assertEquals(1.5, account.getApr(), "Account APR should match.");
		assertEquals(1000.0, account.getBalance(), "Initial deposit should match.");
	}

	@Test
	public void testProcessValidDepositCommand() {
		processor.process("create checking 12345678 1.0");
		processor.process("deposit 12345678 200");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(200.0, account.getBalance(), "Account balance should reflect the deposit.");
	}

	@Test
	public void testProcessMultipleDeposits() {
		processor.process("create savings 87654321 2.5");
		processor.process("deposit 87654321 300");
		processor.process("deposit 87654321 200");

		Account account = bank.getAccount("87654321");
		assertNotNull(account, "Account should exist.");
		assertEquals(500.0, account.getBalance(), "Account balance should reflect all deposits.");
	}

	// Invalid Command Tests
	@Test
	public void testProcessInvalidCommandIgnored() {
		processor.process("invalid command");

		assertEquals(0, bank.getAccountsCount(), "Bank should not create any accounts for invalid commands.");
	}

	@Test
	public void testProcessInvalidCreateCommandIgnored() {
		processor.process("create premium 12345678 1.5");

		assertNull(bank.getAccount("12345678"), "Bank should not create an account for invalid create command.");
	}

	@Test
	public void testProcessInvalidDepositCommandIgnored() {
		processor.process("deposit 99999999 500");

		assertEquals(0, bank.getAccountsCount(), "Bank should not perform deposit for nonexistent accounts.");
	}

	@Test
	public void testProcessCreateCommandWithDuplicateId() {
		processor.process("create checking 12345678 1.0");
		processor.process("create checking 12345678 2.0");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(1.0, account.getApr(), "APR should match the first created account.");
	}

	@Test
	public void testProcessDepositWithNonNumericAmountIgnored() {
		processor.process("create checking 12345678 1.0");
		processor.process("deposit 12345678 abc");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(0.0, account.getBalance(), "Account balance should not change for invalid deposit.");
	}
}
