package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	MasterControl masterControl;
	List<String> input;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(bank, new CommandValidator(bank), new CommandProcessor(bank),
				new CommandStorage());
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 12345678 1.0", actual); // Expecting an invalid command
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("depositt 12345678 100", actual); // Expecting an invalid command
	}

	@Test
	void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0)); // Expecting invalid create command
		assertEquals("depositt 12345678 100", actual.get(1)); // Expecting invalid deposit command
	}

	@Test
	void invalid_to_create_accounts_with_same_ID() {
		input.add("create checking 12345678 1.0");
		input.add("create checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 0.00 1.00", actual.get(0)); // Expecting original command to fail
		assertEquals("create checking 12345678 1.0", actual.get(1)); // Expecting duplicate account creation error
	}

	@Test
	void typo_in_pass_command() {
		input.add("create checking 12345678 1.0");
		input.add("passs 87");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 0.00 1.00", actual.get(0)); // Expecting valid account creation output
		assertEquals("passs 87", actual.get(1)); // Expecting invalid 'pass' command
	}

	@Test
	void invalid_typo_in_withdraw_command() {
		input.add("Widthdrawl 123456789 300");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("Widthdrawl 123456789 300", actual); // Expecting invalid withdraw command
	}

	@Test
	void typo_in_withdraw_command() {
		input.add("create checking 12345678 1.0");
		input.add("withdrawlw 123456789 300");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 0.00 1.00", actual.get(0)); // Expecting valid account creation output
		assertEquals("withdrawlw 123456789 300", actual.get(1)); // Expecting invalid withdraw command
	}

	@Test
	void typo_in_transfer_command_invalid() {
		input.add("transferer 32345678  1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("transferer 32345678  1.0", actual); // Expecting invalid transfer command
	}

	@Test
	void multiple_typo_found_except_1() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");
		input.add("withdrawll 123456789 300");
		input.add("pass 2"); // Pass is not an output

		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0)); // Invalid command
		assertEquals("depositt 12345678 100", actual.get(1)); // Invalid command
		assertEquals("withdrawll 123456789 300", actual.get(2)); // Invalid command
	}

	@Test
	void case_insensitive_test() {
		input.add("creAte cHecKing 12345678 1.0");
		input.add("deposit 12345678 300");

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());

		assertEquals("Checking 12345678 300.00 1.00", actual.get(0));
		assertEquals("Deposit 12345678 300", actual.get(1));// Expecting case-insensitive handling of commands
	}

	@Test
	void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}
}
