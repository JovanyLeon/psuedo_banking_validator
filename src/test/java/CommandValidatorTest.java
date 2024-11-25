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
		assertTrue(actual, "Valid create checking command should return true.");
	}

	@Test
	public void testValidCreateSavingsCommand() {
		boolean actual = validator.validate("create savings 87654321 2.0");
		assertTrue(actual, "Valid create savings command should return true.");
	}

	@Test
	public void testValidCreateCDCommand() {
		boolean actual = validator.validate("create cd 11223344 1.5 1000");
		assertTrue(actual, "Valid create CD command should return true.");
	}

	@Test
	public void testValidDepositCommand() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("deposit 12345678 500");
		assertTrue(actual, "Valid deposit command should return true.");
	}

	// Invalid Test Cases
	@Test
	public void testInvalidNegativeAPR() {
		boolean actual = validator.validate("create checking 12345678 -1.5");
		assertFalse(actual, "Create command with negative APR should return false.");
	}

	@Test
	public void testInvalidCommandType() {
		boolean actual = validator.validate("remove checking 12345678");
		assertFalse(actual, "Invalid command type should return false.");
	}

	@Test
	public void testInvalidDepositToNonexistentAccount() {
		boolean actual = validator.validate("deposit 99999999 500");
		assertFalse(actual, "Deposit command to nonexistent account should return false.");
	}

	@Test
	public void testInvalidTooFewArgumentsCreate() {
		boolean actual = validator.validate("create checking 12345678");
		assertFalse(actual, "Create command with too few arguments should return false.");
	}

	@Test
	public void testInvalidTooFewArgumentsDeposit() {
		boolean actual = validator.validate("deposit 12345678");
		assertFalse(actual, "Deposit command with too few arguments should return false.");
	}

	@Test
	public void testInvalidTooManyArgumentsCreate() {
		boolean actual = validator.validate("create cd 11223344 1.5 1000 extra");
		assertFalse(actual, "Create command with too many arguments should return false.");
	}

	@Test
	public void testInvalidNonNumericAPR() {
		boolean actual = validator.validate("create checking 12345678 abc");
		assertFalse(actual, "Create command with non-numeric APR should return false.");
	}

	@Test
	public void testInvalidNonNumericDepositAmount() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("deposit 12345678 five_hundred");
		assertFalse(actual, "Deposit command with non-numeric amount should return false.");
	}

	@Test
	public void testInvalidNegativeDepositAmount() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("deposit 12345678 -500");
		assertFalse(actual, "Deposit command with negative amount should return false.");
	}

	@Test
	public void testInvalidEmptyCommand() {
		boolean actual = validator.validate("");
		assertFalse(actual, "Empty command should return false.");
	}

	@Test
	public void testInvalidWhitespaceOnlyCommand() {
		boolean actual = validator.validate("   ");
		assertFalse(actual, "Whitespace-only command should return false.");
	}

	@Test
	public void testInvalidNullCommand() {
		boolean actual = validator.validate(null);
		assertFalse(actual, "Null command should return false.");
	}

	@Test
	public void testInvalidUnsupportedAccountType() {
		boolean actual = validator.validate("create premium 12345678 1.5");
		assertFalse(actual, "Create command with unsupported account type should return false.");
	}

	@Test
	public void testInvalidDuplicateAccountId() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("create checking 12345678 1.5");
		assertFalse(actual, "Create command with duplicate account ID should return false.");
	}

	@Test
	public void testInvalidDepositWithCurrencySymbol() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("deposit 12345678 $500");
		assertFalse(actual, "Deposit command with currency symbol in amount should return false.");
	}

	@Test
	public void testValidZeroAPR() {
		boolean actual = validator.validate("create checking 12345678 0.0");
		assertTrue(actual, "Create command with zero APR should return true.");
	}

	@Test
	public void testInvalidNegativeDeposit() {
		boolean actual = validator.validate("deposit 12345678 -100");
		assertFalse(actual, "Deposit command with negative amount should return false.");
	}

	@Test
	public void testInvalidZeroDeposit() {
		boolean actual = validator.validate("deposit 12345678 0");
		assertFalse(actual, "Deposit command with zero amount should return false.");
	}

	@Test
	public void testInvalidNonExistentAccountId() {
		// Invalid account ID that doesn't exist in the bank
		boolean valid = validator.validate("deposit 12345678 1000");
		assertFalse(valid, "Account ID that doesn't exist should return false.");
	}

}