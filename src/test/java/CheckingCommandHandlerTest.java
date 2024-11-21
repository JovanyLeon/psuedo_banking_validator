import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingCommandHandlerTest {

	private Bank bank;
	private CheckingCommandHandler handler;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		handler = new CheckingCommandHandler(bank);
	}

	@Test
	public void testCreateCheckingAccount() {
		String result = handler.processCreateCommand("create checking C123 1.5");
		assertEquals("Checking account created successfully.", result);

		Account account = bank.getAccount("C123");
		assertNotNull(account);
		assertEquals("C123", account.getAccountId());
		assertEquals(1.5, account.getApr(), 0.01);
		assertEquals(0.0, account.getBalance(), 0.01);
	}

	@Test
	public void testInvalidCheckingCommand() {
		String result = handler.processCreateCommand("create checking C123");
		assertEquals("Invalid arguments for create checking.", result);

		result = handler.processCreateCommand("create checking");
		assertEquals("Invalid arguments for create checking.", result);
	}
}