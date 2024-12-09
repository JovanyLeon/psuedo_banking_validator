package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandProcessorTest {
	private Bank bank;
	private TransferCommandProcessor processor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		processor = new TransferCommandProcessor(bank);
	}

	@Test
	public void TEST_PROCESS_VALID_TRANSFER() {
		bank.createCheckingAccount("12345678", 1.0);
		bank.createCheckingAccount("87654321", 2.0);
		bank.getAccount("12345678").deposit(500);
		processor.process("transfer 12345678 87654321 300");

		Account fromAccount = bank.getAccount("12345678");
		Account toAccount = bank.getAccount("87654321");

		assertNotNull(fromAccount, "From account should exist.");
		assertNotNull(toAccount, "To account should exist.");
		assertEquals(200.0, fromAccount.getBalance(), "From account balance should reflect the withdrawal.");
		assertEquals(300.0, toAccount.getBalance(), "To account balance should reflect the deposit.");
	}

	@Test
	public void TEST_PROCESS_TRANSFER_MORE_THAN_BALANCE() {
		bank.createCheckingAccount("12345678", 1.0);
		bank.createCheckingAccount("87654321", 2.0);
		bank.getAccount("12345678").deposit(200);
		processor.process("transfer 12345678 87654321 300");

		Account fromAccount = bank.getAccount("12345678");
		Account toAccount = bank.getAccount("87654321");

		assertNotNull(fromAccount, "From account should exist.");
		assertNotNull(toAccount, "To account should exist.");
		assertEquals(0.0, fromAccount.getBalance(), "From account balance should be zero.");
		assertEquals(200.0, toAccount.getBalance(), "To account balance should reflect the transfer amount.");
	}

	@Test
	public void TEST_PROCESS_INVALID_TRANSFER_IGNORED() {
		bank.createCheckingAccount("12345678", 1.0);
		bank.createCDAccount("11223344", 2.0, 2000);
		bank.getAccount("12345678").deposit(500);
		processor.process("transfer 12345678 11223344 300");

		Account fromAccount = bank.getAccount("12345678");
		Account toAccount = bank.getAccount("11223344");

		assertNotNull(fromAccount, "From account should exist.");
		assertNotNull(toAccount, "To account should exist.");
		assertEquals(500.0, fromAccount.getBalance(), "From account balance should not change.");
		assertEquals(2000.0, toAccount.getBalance(), "To account balance should not change.");
	}
}
