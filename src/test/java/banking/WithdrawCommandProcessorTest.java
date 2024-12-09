package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandProcessorTest {
	private Bank bank;
	private WithdrawCommandProcessor processor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		processor = new WithdrawCommandProcessor(bank);
	}

	@Test
	public void testProcessValidWithdrawCheckingCommand() {
		bank.createCheckingAccount("12345678", 1.0);
		bank.getAccount("12345678").deposit(500);
		processor.process("withdraw 12345678 300");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(200.0, account.getBalance(), "Account balance should reflect the withdrawal.");
	}

	@Test
	public void testProcessValidWithdrawSavingsCommand() {
		bank.createSavingsAccount("87654321", 2.5);
		bank.getAccount("87654321").deposit(1500);
		processor.process("withdraw 87654321 800");

		Account account = bank.getAccount("87654321");
		assertNotNull(account, "Account should exist.");
		assertEquals(700.0, account.getBalance(), "Account balance should reflect the withdrawal.");
	}

	@Test
	public void testProcessInvalidWithdrawCommandIgnored() {
		bank.createCheckingAccount("12345678", 1.0);
		bank.getAccount("12345678").deposit(500);
		processor.process("withdraw 12345678 5000");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(500.0, account.getBalance(), "Account balance should not change for invalid withdrawal.");
	}

	@Test
	public void testProcessWithdrawMoreThanBalanceSetsToZero() {
		bank.createCheckingAccount("12345678", 1.0);
		bank.getAccount("12345678").deposit(300);
		processor.process("withdraw 12345678 400");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(0.0, account.getBalance(), "Account balance should be set to zero if withdrawal exceeds balance.");
	}

	@Test
	public void testProcessWithdrawCDCommandAfter12Months() {
		bank.createCDAccount("11223344", 1.5, 2000);
		bank.passMonths(12); // Simulate 12 months passing
		processor.process("withdraw 11223344 2000");

		Account account = bank.getAccount("11223344");
		assertNotNull(account, "CD Account should exist.");
		assertEquals(0.0, account.getBalance(), "Account balance should be zero after full withdrawal.");
	}
}
