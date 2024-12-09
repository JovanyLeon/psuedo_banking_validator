package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandProcessorTest {
	private Bank bank;
	private DepositCommandProcessor processor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		processor = new DepositCommandProcessor(bank);
	}

	@Test
	public void testProcessValidDepositCommand() {
		bank.createCheckingAccount("12345678", 1.0);
		processor.process("deposit 12345678 200");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(200.0, account.getBalance(), "Account balance should reflect the deposit.");
	}

	@Test
	public void testProcessMultipleDeposits() {
		bank.createSavingsAccount("87654321", 2.5);
		processor.process("deposit 87654321 300");
		processor.process("deposit 87654321 200");

		Account account = bank.getAccount("87654321");
		assertNotNull(account, "Account should exist.");
		assertEquals(500.0, account.getBalance(), "Account balance should reflect all deposits.");
	}

	@Test
	public void testProcessInvalidDepositCommandIgnored() {
		processor.process("deposit 99999999 500");

		assertEquals(0, bank.getAccountsCount(), "Bank should not perform deposit for nonexistent accounts.");
	}

	@Test
	public void testProcessDepositWithNonNumericAmountIgnored() {
		bank.createCheckingAccount("12345678", 1.0);
		processor.process("deposit 12345678 abc");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(0.0, account.getBalance(), "Account balance should not change for invalid deposit.");
	}

	@Test
	public void testProcessDepositExceedsCheckingLimitIgnored() {
		bank.createCheckingAccount("12345678", 1.0);
		processor.process("deposit 12345678 1500");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(0.0, account.getBalance(), "Account balance should not change for invalid deposit.");
	}

	@Test
	public void testProcessDepositExceedsSavingsLimitIgnored() {
		bank.createSavingsAccount("87654321", 2.0);
		processor.process("deposit 87654321 3000");

		Account account = bank.getAccount("87654321");
		assertNotNull(account, "Account should exist.");
		assertEquals(0.0, account.getBalance(), "Account balance should not change for invalid deposit.");
	}

	@Test
	public void testProcessDepositIntoCDIgnored() {
		bank.createCDAccount("11223344", 1.5, 1000);
		processor.process("deposit 11223344 500");

		Account account = bank.getAccount("11223344");
		assertNotNull(account, "Account should exist.");
		assertEquals(1000.0, account.getBalance(), "Account balance should not change for invalid deposit.");
	}

	@Test
	void test_case_insensitive() {
		bank.createSavingsAccount("87654321", 2.0);
		processor.process("dePosIt 87654321 300");

		Account account = bank.getAccount("87654321");

		assertEquals(300, account.getBalance());
	}
}
