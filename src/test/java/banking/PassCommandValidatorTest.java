package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PassCommandValidatorTest {

	@Test
	public void validSingleMonth() {
		PassCommandValidator validator = new PassCommandValidator();
		assertTrue(validator.validate("pass 1")); // Minimum valid months
	}

	@Test
	public void validMultipleMonths() {
		PassCommandValidator validator = new PassCommandValidator();
		assertTrue(validator.validate("pass 12")); // Typical valid case
		assertTrue(validator.validate("pass 60")); // Maximum valid months
	}

	@Test
	public void invalidZeroMonths() {
		PassCommandValidator validator = new PassCommandValidator();
		assertFalse(validator.validate("pass 0")); // Less than minimum months
	}

	@Test
	public void invalidExcessiveMonths() {
		PassCommandValidator validator = new PassCommandValidator();
		assertFalse(validator.validate("pass 61")); // Greater than maximum months
	}

	@Test
	public void invalidCommandFormat() {
		PassCommandValidator validator = new PassCommandValidator();
		assertFalse(validator.validate("pass")); // Missing months
		assertFalse(validator.validate("pass sixty")); // Invalid number format
		assertFalse(validator.validate("skip 12")); // Wrong command keyword
		assertFalse(validator.validate("pass 12 extra")); // Extra arguments
	}

	@Test
	public void invalidEmptyCommand() {
		PassCommandValidator validator = new PassCommandValidator();
		assertFalse(validator.validate("")); // Empty command
		assertFalse(validator.validate("     ")); // Only whitespace
	}

	@Test
	public void caseInsensitiveValidation() {
		PassCommandValidator validator = new PassCommandValidator();
		assertTrue(validator.validate("PASS 12")); // Uppercase command keyword
		assertTrue(validator.validate("PaSs 5")); // Mixed case command keyword
	}
}