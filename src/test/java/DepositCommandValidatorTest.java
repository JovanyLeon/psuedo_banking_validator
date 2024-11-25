import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandValidatorTest {
	private Bank bank;
	private DepositCommandValidator validator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validator = new DepositCommandValidator(bank);
	}

	@Test
	public void testValidDepositCommand() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate(new String[] { "deposit", "12345678", "500" });
		assertTrue(actual);
	}

	@Test
	public void testInvalidDepositToNonexistentAccount() {
		boolean actual = validator.validate(new String[] { "deposit", "99999999", "500" });
		assertFalse(actual);
	}

	@Test
	public void testInvalidTooFewArguments() {
		boolean actual = validator.validate(new String[] { "deposit", "12345678" });
		assertFalse(actual);
	}

	@Test
	public void testInvalidNonNumericDepositAmount() {
		bank.createSavingsAccount("87654321", 2.0);
		boolean actual = validator.validate(new String[] { "deposit", "87654321", "five_hundred" });
		assertFalse(actual);
	}

	@Test
	public void testInvalidNegativeDepositAmount() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate(new String[] { "deposit", "12345678", "-500" });
		assertFalse(actual);
	}

	@Test
	public void testInvalidDepositZeroAmount() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate(new String[] { "deposit", "12345678", "0" });
		assertFalse(actual);
	}

	@Test
	public void testInvalidDepositWithCurrencySymbol() {
		bank.createSavingsAccount("87654321", 2.0);
		boolean actual = validator.validate(new String[] { "deposit", "87654321", "$500" });
		assertFalse(actual);
	}
}