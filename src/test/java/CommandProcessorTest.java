import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	private Bank bank;
	private CommandValidator validator;
	private CommandProcessor processor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validator = new CommandValidator(bank);
		processor = new CommandProcessor(bank, validator);
	}

	@Test
	public void testCreateCheckingAccount() {
		String result = processor.process("create checking 12345678 1.5");
		assertEquals("Checking account created", result);
		assertNotNull(bank.getAccount("12345678"));
	}

	@Test
	public void testCreateSavingsAccount() {
		String result = processor.process("create savings 87654321 2.0");
		assertEquals("Savings account created", result);
		assertNotNull(bank.getAccount("87654321"));
	}

	@Test
	public void testCreateCDAccount() {
		String result = processor.process("create cd 11223344 3.0 5000");
		assertEquals("CD account created", result);
		Account account = bank.getAccount("11223344");
		assertNotNull(account);
		assertEquals(5000, account.getBalance(), 0.01);
	}

	@Test
	public void testInvalidCreateCommand() {
		String result = processor.process("create checking 12345678 -1.5");
		assertEquals("Invalid command", result);
		assertNull(bank.getAccount("12345678"));
	}

	@Test
	public void testDuplicateAccountId() {
		processor.process("create checking 12345678 1.5");
		String result = processor.process("create checking 12345678 2.0");
		assertEquals("Invalid command", result);
	}

	@Test
	public void testDepositSuccess() {
		processor.process("create checking 12345678 1.5");
		String result = processor.process("deposit 12345678 1000");
		assertEquals("Deposit successful", result);
		assertEquals(1000, bank.getAccount("12345678").getBalance(), 0.01);
	}

	@Test
	public void testDepositToNonExistentAccount() {
		String result = processor.process("deposit 99999999 1000");
		assertEquals("Account not found", result);
	}

	@Test
	public void testWithdrawSuccess() {
		processor.process("create checking 12345678 1.5");
		processor.process("deposit 12345678 500");
		String result = processor.process("withdraw 12345678 300");
		assertEquals("Withdrawal successful", result);
		assertEquals(200, bank.getAccount("12345678").getBalance(), 0.01);
	}

	@Test
	public void testWithdrawInsufficientFunds() {
		processor.process("create checking 12345678 1.5");
		processor.process("deposit 12345678 200");
		String result = processor.process("withdraw 12345678 500");
		assertEquals("Withdrawal successful", result);
		assertEquals(0, bank.getAccount("12345678").getBalance(), 0.01);
	}

	@Test
	public void testWithdrawFromNonExistentAccount() {
		String result = processor.process("withdraw 99999999 500");
		assertEquals("Account not found", result);
	}

	@Test
	public void testInvalidCommandFormat() {
		String result = processor.process("create checking");
		assertEquals("Invalid command", result);
	}

	@Test
	public void testUnsupportedCommand() {
		String result = processor.process("delete 12345678");
		assertEquals("Unsupported operation", result);
	}
}
