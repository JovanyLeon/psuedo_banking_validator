package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandValidatorTest {
	private Bank bank;
	private WithdrawCommandValidator validator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validator = new WithdrawCommandValidator(bank);
	}

	@Test
	public void test_Valid_Withdraw_Checking_Command() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.getAccount("12345678").deposit(500);
		boolean actual = validator.validate("withdraw 12345678 300");
		assertTrue(actual);
	}

	@Test
	public void testValidWithdrawSavingsCommand() {
		bank.createSavingsAccount("87654321", 2.0);
		bank.getAccount("87654321").deposit(1500);
		boolean actual = validator.validate("withdraw 87654321 800");
		assertTrue(actual);
	}

	@Test
	public void testInvalidWithdrawCheckingExceedsLimit() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.getAccount("12345678").deposit(500);
		boolean actual = validator.validate("withdraw 12345678 500");
		assertFalse(actual);
	}

	@Test
	public void testInvalidWithdrawSavingsExceedsLimit() {
		bank.createSavingsAccount("87654321", 2.0);
		bank.getAccount("87654321").deposit(1500);
		boolean actual = validator.validate("withdraw 87654321 1200");
		assertFalse(actual);
	}

	@Test
	public void testInvalidWithdrawSavingsMultipleWithdrawals() {
		bank.createSavingsAccount("87654321", 2.0);
		bank.getAccount("87654321").deposit(1500);
		bank.getAccount("87654321").withdraw(500); // First withdrawal in the month
		boolean actual = validator.validate("withdraw 87654321 300");
		assertFalse(actual);
	}

	@Test
	public void testValidWithdrawCDCommandAfter12Months() {
		bank.createCDAccount("11223344", 1.5, 2000);
		bank.passMonths(12); // Simulate 12 months passing
		boolean actual = validator.validate("withdraw 11223344 2000");
		assertTrue(actual);
	}

	@Test
	public void testInvalidWithdrawCDCommandBefore12Months() {
		bank.createCDAccount("11223344", 1.5, 2000);
		boolean actual = validator.validate("withdraw 11223344 2000");
		assertFalse(actual);
	}

	@Test
	public void testInvalidWithdrawCDCommandPartialAmount() {
		bank.createCDAccount("11223344", 1.5, 2000);
		bank.passMonths(12); // Simulate 12 months passing
		boolean actual = validator.validate("withdraw 11223344 1000");
		assertFalse(actual);
	}

	@Test
	public void testInvalidWithdrawNegativeAmount() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.getAccount("12345678").deposit(500);
		boolean actual = validator.validate("withdraw 12345678 -200");
		assertFalse(actual);
	}

	@Test
	public void testValidWithdrawZeroAmount() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.getAccount("12345678").deposit(500);
		boolean actual = validator.validate("withdraw 12345678 0");
		assertTrue(actual);
	}
}
