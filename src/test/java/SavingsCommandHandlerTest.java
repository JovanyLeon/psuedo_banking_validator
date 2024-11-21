import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsCommandHandlerTest {

	private Bank bank;
	private SavingsCommandHandler handler;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		handler = new SavingsCommandHandler(bank);
	}

	@Test
	public void testCreateSavingsAccount() {
		String result = handler.processCreateCommand("create savings S123 2.0");
		assertEquals("Savings account created successfully.", result);

		Account account = bank.getAccount("S123");
		assertNotNull(account);
		assertEquals("S123", account.getAccountId());
		assertEquals(2.0, account.getApr(), 0.01);
		assertEquals(0.0, account.getBalance(), 0.01);
	}

	@Test
	public void testInvalidSavingsCommand() {
		String result = handler.processCreateCommand("create savings");
		assertEquals("Invalid arguments for create savings.", result);
	}
}