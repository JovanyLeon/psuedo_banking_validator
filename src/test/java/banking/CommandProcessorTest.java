package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	private Bank bank;
	private CommandProcessor processor;
	private CommandStorage commandStorage;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		processor = new CommandProcessor(bank);
		this.commandStorage = commandStorage;

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

	@Test
	public void testProcessValidWithdrawCommand() {
		processor.process("create checking 12345678 1.0");
		processor.process("deposit 12345678 500");
		processor.process("withdraw 12345678 300");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(200.0, account.getBalance(), "Account balance should reflect the withdrawal.");
	}

	@Test
	public void testProcessValidTransferCommand() {
		processor.process("create checking 12345678 1.0");
		processor.process("create savings 87654321 2.5");
		processor.process("deposit 12345678 500");
		processor.process("transfer 12345678 87654321 300");

		Account fromAccount = bank.getAccount("12345678");
		Account toAccount = bank.getAccount("87654321");

		assertNotNull(fromAccount, "From account should exist.");
		assertNotNull(toAccount, "To account should exist.");
		assertEquals(200.0, fromAccount.getBalance(), "From account balance should reflect the withdrawal.");
		assertEquals(300.0, toAccount.getBalance(), "To account balance should reflect the deposit.");
	}

	@Test
	public void testProcessValidPassCommand() {
		processor.process("create checking 12345678 1.0");
		processor.process("deposit 12345678 200");
		processor.process("pass 1");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(200.17, account.getBalance(), 0.01, "Account balance should reflect the interest after 1 month.");
	}

	@Test
	public void testCDAccountPassCommand() {
		processor.process("create cd 12345678 2.1 2000");
		processor.process("pass 1");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(2014.03, account.getBalance(), 0.01);
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

	@Test
	public void testProcessWithdrawWithNonNumericAmountIgnored() {
		processor.process("create checking 12345678 1.0");
		processor.process("deposit 12345678 500");
		processor.process("withdraw 12345678 abc");

		Account account = bank.getAccount("12345678");
		assertNotNull(account, "Account should exist.");
		assertEquals(500.0, account.getBalance(), "Account balance should not change for invalid withdrawal.");
	}

	@Test
	public void testProcessTransferWithNonNumericAmountIgnored() {
		processor.process("create checking 12345678 1.0");
		processor.process("create savings 87654321 2.5");
		processor.process("deposit 12345678 500");
		processor.process("transfer 12345678 87654321 abc");

		Account fromAccount = bank.getAccount("12345678");
		Account toAccount = bank.getAccount("87654321");

		assertNotNull(fromAccount, "From account should exist.");
		assertNotNull(toAccount, "To account should exist.");
		assertEquals(500.0, fromAccount.getBalance(), "From account balance should not change for invalid transfer.");
		assertEquals(0.0, toAccount.getBalance(), "To account balance should not change for invalid transfer.");
	}

	@Test
	public void testAllCommands() {
		processor.process("create savings 12345678 .6");
		processor.process("deposit 12345678 700");
		processor.process("deposit 12345678 5000");
		processor.process("create checking 98765432 0.01");
		processor.process("deposit 98765432 300");
		processor.process("transfer 98765432 12345678 300");
		processor.process("pass 1");
		processor.process("create cd 23456789 1.2 2000");
		processor.process("withdraw 12345678 300");

		Account savings = bank.getAccount("12345678");
		Account cd = bank.getAccount("23456789");

		assertEquals(700.5, savings.getBalance());
		assertEquals(2000, cd.getBalance());
	}

	@Test
	void test_case_insensitive() {
		bank.createSavingsAccount("87654321", 2.0);
		processor.process("dePosIt 87654321 300");

		Account account = bank.getAccount("87654321");

		assertEquals(300, account.getBalance());
	}

}
