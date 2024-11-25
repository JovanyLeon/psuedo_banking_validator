import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	private Bank bank;
	private CommandValidator validator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validator = new CommandValidator(bank);
	}

	@Test
	public void testValidCreateCommand() {
		boolean actual = validator.validate("create checking 12345678 1.5");
		assertTrue(actual, "Valid create command should return true.");
	}

	@Test
	public void testDuplicateAccountId() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("create checking 12345678 1.5");
		assertFalse(actual, "Duplicate account ID should return false.");
	}

	@Test
	public void testInvalidCreateCommandFormat() {
		boolean actual = validator.validate("create checking 12345678");
		assertFalse(actual, "Create command with insufficient arguments should return false.");
	}

	@Test
	public void testValidDepositCommand() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("deposit 12345678 500");
		assertTrue(actual, "Valid deposit command should return true.");
	}

	@Test
	public void testInvalidDepositCommandFormat() {
		boolean actual = validator.validate("deposit 12345678");
		assertFalse(actual, "Deposit command with insufficient arguments should return false.");
	}
}