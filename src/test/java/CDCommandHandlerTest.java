import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDCommandHandlerTest {

	private Bank bank;
	private CDCommandHandler handler;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		handler = new CDCommandHandler(bank);
	}

	@Test
	public void testCreateCDAccount() {
		String result = handler.processCreateCommand("create cd CD123 3.0 1000");
		assertEquals("CD account created successfully.", result);

		Account account = bank.getAccount("CD123");
		assertNotNull(account);
		assertEquals("CD123", account.getAccountId());
		assertEquals(3.0, account.getApr(), 0.01);
		assertEquals(1000, account.getBalance(), 0.01);
	}

	@Test
	public void testInvalidCDCommand() {
		String result = handler.processCreateCommand("create cd");
		assertEquals("Invalid arguments for create cd.", result);

		result = handler.processCreateCommand("create cd CD123 3.0");
		assertEquals("Invalid arguments for create cd.", result);
	}
}
