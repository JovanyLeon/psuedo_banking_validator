package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCommandValidatorTest {
	private Bank bank;
	private CreateCommandValidator validator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validator = new CreateCommandValidator(bank);
	}

	@Test
	public void testValidCreateCheckingCommand() {
		boolean actual = validator.validate("create checking 12345678 1.5");
		assertTrue(actual);
	}

	@Test
	public void testValidCreateSavingsCommand() {
		boolean actual = validator.validate("create savings 87654321 2.0");
		assertTrue(actual);
	}

	@Test
	public void testValidCreateCDCommand() {
		boolean actual = validator.validate("create cd 11223344 1.5 1000");
		assertTrue(actual);
	}

	@Test
	public void testInvalidDuplicateAccountId() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("create checking 12345678 1.5");
		assertFalse(actual);
	}

	@Test
	public void testInvalidNegativeAPR() {
		boolean actual = validator.validate("create savings 87654321 -2.0");
		assertFalse(actual);
	}

	@Test
	public void testInvalidTooFewArguments() {
		boolean actual = validator.validate("create checking 12345678");
		assertFalse(actual);
	}

	@Test
	public void testInvalidTooManyArguments() {
		boolean actual = validator.validate("create cd 11223344 1.5 1000 extra");
		assertFalse(actual);
	}

	@Test
	public void testInvalidUnsupportedAccountType() {
		boolean actual = validator.validate("create premium 12345678 1.5");
		assertFalse(actual);
	}

	@Test
	public void testInvalidNonNumericAPR() {
		boolean actual = validator.validate("create checking 12345678 abc");
		assertFalse(actual);
	}

	@Test
	public void testInvalidAPRGreaterThan10() {
		boolean actual = validator.validate("create savings 87654321 10.5");
		assertFalse(actual);
	}

	@Test
	public void testInvalidCDInitialDepositLessThan1000() {
		boolean actual = validator.validate("create cd 11223344 1.5 500");
		assertFalse(actual);
	}

	@Test
	public void testInvalidCDInitialDepositGreaterThan10000() {
		boolean actual = validator.validate("create cd 11223344 1.5 15000");
		assertFalse(actual);
	}
}
