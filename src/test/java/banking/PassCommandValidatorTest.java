package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassCommandValidatorTest {
	private Bank bank;
	private PassCommandValidator validator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validator = new PassCommandValidator(bank);
	}

	@Test
	public void VALID_SINGLE_MONTH() {
		assertTrue(validator.validate("pass 1")); // Minimum valid months
	}

	@Test
	public void VALID_MULTIPLE_MONTHS() {
		assertTrue(validator.validate("pass 12")); // Typical valid case
		assertTrue(validator.validate("pass 60")); // Maximum valid months
	}

	@Test
	public void INVALID_ZERO_MONTHS() {
		assertFalse(validator.validate("pass 0")); // Less than minimum months
	}

	@Test
	public void INVALID_EXCESSIVE_MONTHS() {
		assertFalse(validator.validate("pass 61")); // Greater than maximum months
	}

	@Test
	public void INVALID_COMMAND_FORMAT() {
		assertFalse(validator.validate("pass")); // Missing months
		assertFalse(validator.validate("pass sixty")); // Invalid number format
		assertFalse(validator.validate("skip 12")); // Wrong command keyword
		assertFalse(validator.validate("pass 12 extra")); // Extra arguments
	}

	@Test
	public void INVALID_EMPTY_COMMAND() {
		assertFalse(validator.validate("")); // Empty command
		assertFalse(validator.validate("     ")); // Only whitespace
	}

	@Test
	public void CASE_INSENSITIVE_VALIDATION() {
		assertTrue(validator.validate("PASS 12")); // Uppercase command keyword
		assertTrue(validator.validate("PaSs 5")); // Mixed case command keyword
	}
}
