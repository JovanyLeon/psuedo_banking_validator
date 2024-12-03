package banking;

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

	// Valid Test Cases
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
	public void testValidDepositCommand() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("deposit 12345678 500");
		assertTrue(actual);
	}

	// Invalid Test Cases
	@Test
	public void testInvalidNegativeAPR() {
		boolean actual = validator.validate("create checking 12345678 -1.5");
		assertFalse(actual);
	}

	@Test
	public void testInvalidCommandType() {
		boolean actual = validator.validate("remove checking 12345678");
		assertFalse(actual);
	}

	@Test
	public void testInvalidDepositToNonexistentAccount() {
		boolean actual = validator.validate("deposit 99999999 500");
		assertFalse(actual);
	}

	@Test
	public void testInvalidTooFewArgumentsCreate() {
		boolean actual = validator.validate("create checking 12345678");
		assertFalse(actual);
	}

	@Test
	public void testInvalidTooFewArgumentsDeposit() {
		boolean actual = validator.validate("deposit 12345678");
		assertFalse(actual);
	}

	@Test
	public void testInvalidTooManyArgumentsCreate() {
		boolean actual = validator.validate("create cd 11223344 1.5 1000 extra");
		assertFalse(actual);
	}

	@Test
	public void testInvalidNonNumericAPR() {
		boolean actual = validator.validate("create checking 12345678 abc");
		assertFalse(actual);
	}

	@Test
	public void testInvalidNonNumericDepositAmount() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("deposit 12345678 five_hundred");
		assertFalse(actual);
	}

	@Test
	public void testInvalidNegativeDepositAmount() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("deposit 12345678 -500");
		assertFalse(actual);
	}

	@Test
	public void testInvalidEmptyCommand() {
		boolean actual = validator.validate("");
		assertFalse(actual);
	}

	@Test
	public void testInvalidWhitespaceOnlyCommand() {
		boolean actual = validator.validate("   ");
		assertFalse(actual);
	}

	@Test
	public void testInvalidNullCommand() {
		boolean actual = validator.validate(null);
		assertFalse(actual);
	}

	@Test
	public void testInvalidUnsupportedAccountType() {
		boolean actual = validator.validate("create premium 12345678 1.5");
		assertFalse(actual);
	}

	@Test
	public void testInvalidDuplicateAccountId() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("create checking 12345678 1.5");
		assertFalse(actual);
	}

	@Test
	public void testInvalidDepositWithCurrencySymbol() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("deposit 12345678 $500");
		assertFalse(actual);
	}

	@Test
	public void testValidZeroAPR() {
		boolean actual = validator.validate("create checking 12345678 0.0");
		assertTrue(actual);
	}

	@Test
	public void testInvalidNegativeDeposit() {
		boolean actual = validator.validate("deposit 12345678 -100");
		assertFalse(actual);
	}

	@Test
	public void testInvalidZeroDeposit() {
		boolean actual = validator.validate("deposit 12345678 0");
		assertFalse(actual);
	}

	@Test
	public void testInvalidNonExistentAccountId() {
		boolean valid = validator.validate("deposit 12345678 1000");
		assertFalse(valid);
	}
}