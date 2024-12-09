package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCommandProcessorTest {
	private Bank bank;
	private CreateCommandProcessor processor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		processor = new CreateCommandProcessor(bank);
	}

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
	public void testProcessCreateCommandWithDuplicateId() {
		processor.process("create checking 12345678 1.0");
		processor.process("create checking 12345678 2.0");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(1.0, account.getApr(), "APR should match the first created account.");
	}

	@Test
	public void testProcessInvalidCreateCommandIgnored() {
		processor.process("create premium 12345678 1.5"); // Invalid account type "premium"

		// Verify that the account was not created (account should be null)
		assertNull(bank.getAccount("12345678"), "Bank should not create an account for invalid create command.");
	}

	@Test
	public void test_case_insensitive() {
		processor.process("creAte cHecKing 12345678 1.0");
		assertNotNull(bank.getAccount("12345678"));
	}
}
