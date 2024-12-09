package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandValidatorTest {
	private Bank bank;
	private TransferCommandValidator validator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validator = new TransferCommandValidator(bank);
	}

	@Test
	public void TEST_VALID_TRANSFER_CHECKING_TO_CHECKING() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.createCheckingAccount("87654321", 2.0);
		boolean actual = validator.validate("transfer 12345678 87654321 500");
		assertTrue(actual);
	}

	@Test
	public void TEST_VALID_TRANSFER_CHECKING_TO_SAVINGS() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.createSavingsAccount("87654321", 2.0);
		boolean actual = validator.validate("transfer 12345678 87654321 500");
		assertTrue(actual);
	}

	@Test
	public void TEST_INVALID_TRANSFER_FROM_CD() {
		bank.createCDAccount("11223344", 1.5, 2000);
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("transfer 11223344 12345678 500");
		assertFalse(actual);
	}

	@Test
	public void TEST_INVALID_TRANSFER_TO_CD() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.createCDAccount("11223344", 1.5, 2000);
		boolean actual = validator.validate("transfer 12345678 11223344 500");
		assertFalse(actual);
	}

	@Test
	public void TEST_INVALID_TRANSFER_NEGATIVE_AMOUNT() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.createCheckingAccount("87654321", 2.0);
		boolean actual = validator.validate("transfer 12345678 87654321 -500");
		assertFalse(actual);
	}

	@Test
	public void TEST_INVALID_TRANSFER_NONEXISTENT_FROM_ACCOUNT() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("transfer 99999999 12345678 500");
		assertFalse(actual);
	}

	@Test
	public void TEST_INVALID_TRANSFER_NONEXISTENT_TO_ACCOUNT() {
		bank.createCheckingAccount("12345678", 1.5);
		boolean actual = validator.validate("transfer 12345678 99999999 500");
		assertFalse(actual);
	}
}
