package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassCommandProcessorTest {
	private Bank bank;
	private PassCommandProcessor processor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		processor = new PassCommandProcessor(bank);
	}

	@Test
	public void testProcessValidPassCommand() {
		bank.createCheckingAccount("12345678", 0.01);
		bank.createCheckingAccount("87654321", 0.01);

		bank.getAccount("12345678").deposit(200);
		bank.getAccount("87654321").deposit(50);

		assertDoesNotThrow(() -> processor.process("pass 1"));
	}

	@Test
	public void testProcessInvalidPassCommandFormat() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			processor.process("pass");
		});
		assertEquals("Invalid pass command: pass", exception.getMessage());
	}

	@Test
	public void testAccountWithBalance() {
		bank.createCheckingAccount("12345678", 3);

		bank.getAccount("12345678").deposit(1000);

		processor.process("pass 1");

		// After deducting the fee for a balance < $100

		assertEquals(1002.5, bank.getAccount("12345678").getBalance());
	}

	@Test
	public void testMultipleAccountsProcessing() {
		bank.createCheckingAccount("12345678", 3);
		bank.createCheckingAccount("87654321", 3);
		bank.createCheckingAccount("11223344", 3);

		bank.getAccount("12345678").deposit(200);
		bank.getAccount("87654321").deposit(50);
		bank.getAccount("11223344").deposit(0);

		processor.process("pass 1");

		assertEquals(25.06, bank.getAccount("87654321").getBalance()); // After deducting fee
		assertEquals(200.5, bank.getAccount("12345678").getBalance(), 0.001); // After accruing APR
		assertNull(bank.getAccount("11223344"));
	}

	@Test
	public void testAccruingAPR() {
		bank.createCheckingAccount("12345678", 12.0); // 12% APR, so 1% per month
		bank.getAccount("12345678").deposit(1000); // Initial balance

		processor.process("pass 1"); // Pass one month

		// After one month, the balance should be increased by 1%
		assertEquals(1010.0, bank.getAccount("12345678").getBalance()); // Allow small margin for rounding

		processor.process("pass 12"); // Pass twelve more months

		// After twelve more months, balance should account for compounding interest
		double expectedBalance = 1010.0;
		for (int i = 0; i < 12; i++) {
			expectedBalance *= 1.01;
			expectedBalance = Math.round(expectedBalance * 100.0) / 100.0;// Apply 1% per month
		}
		assertEquals(expectedBalance, bank.getAccount("12345678").getBalance()); //
		// Allow small margin for
		// rounding
	}

	@Test
	public void testAPRForMultipleAccounts() {
		bank.createCheckingAccount("12345678", 12.0); // 12% APR, 1% per month
		bank.createSavingsAccount("87654321", 6.0); // 6% APR, 0.5% per month

		bank.getAccount("12345678").deposit(1000); // Initial balance
		bank.getAccount("87654321").deposit(2000); // Initial balance

		processor.process("pass 1"); // Pass one month

		// After one month, the balance should reflect the respective APRs
		assertEquals(1010.0, bank.getAccount("12345678").getBalance(), 0.01); // 1% increase
		assertEquals(2010.0, bank.getAccount("87654321").getBalance(), 0.01); // 0.5% increase

		processor.process("pass 12"); // Pass twelve more months

		// After twelve more months, balance should account for compounding interest
		double expectedBalance1 = 1010.0;
		for (int i = 0; i < 12; i++) {
			expectedBalance1 *= 1.01;
			expectedBalance1 = Math.round(expectedBalance1 * 100.0) / 100.0;
		}
		assertEquals(expectedBalance1, bank.getAccount("12345678").getBalance(), 0.01); // Allow small margin for
																						// rounding

		double expectedBalance2 = 2010.0;
		for (int i = 0; i < 12; i++) {
			expectedBalance2 *= 1.005;
			expectedBalance2 = Math.round(expectedBalance2 * 100.0) / 100.0;
		}
		assertEquals(expectedBalance2, bank.getAccount("87654321").getBalance(), 0.01); // Allow small margin for
		// rounding
	}

	@Test
	public void testAccruingAPRForCD() {
		// Create a CD account with 12% APR (i.e., 3% per quarter)
		bank.createCDAccount("12345678", 12.0, 1000); // 12% APR, compounded monthly in 4 periods per month

		// Process 1 month (compounds 4 times)
		processor.process("pass 1"); // Pass one month, interest compounded 4 times

		// Calculate expected balance after 1 month
		double expectedBalance = 1000.0;
		double monthlyRate = 12.0 / 12 / 100; // 12% APR divided by 4 for monthly compounding
		for (int i = 0; i < 4; i++) {
			expectedBalance *= (1 + monthlyRate);
			expectedBalance = Math.round(expectedBalance * 100.0) / 100.0;// Apply the interest 4 times in a month
		}

		// Check if the balance matches the expected value after one month
		assertEquals(expectedBalance, bank.getAccount("12345678").getBalance(), 0.01);
	}

	@Test
	public void testCheckingAccountAPRForThreeMonths() {
		// Create Checking account with 12% APR (1% per month)
		bank.createCheckingAccount("12345678", 12.0); // Checking account with 12% APR (1% per month)

		// Deposit initial balance
		bank.getAccount("12345678").deposit(1000); // Checking deposit

		// Process 1 month for Checking account (interest applied based on type)
		processor.process("pass 1"); // Pass one month, applies interest

		// Calculate expected balance for Checking account after 1 month
		double expectedCheckingBalance = 1000.0 * 1.01; // 1% interest
		expectedCheckingBalance = Math.round(expectedCheckingBalance * 100.0) / 100.0; // Round to 2 decimal places

		// Assert balance after 1 month
		assertEquals(expectedCheckingBalance, bank.getAccount("12345678").getBalance(), 0.01);

		// Process for the second month for Checking account (interest applied based on
		// type)
		processor.process("pass 1"); // Pass one more month, applies interest

		// Update expected balance after the second month for Checking account
		expectedCheckingBalance *= 1.01; // Apply 1% interest for the second month
		expectedCheckingBalance = Math.round(expectedCheckingBalance * 100.0) / 100.0; // Round to 2 decimal places

		// Assert balance after 2 months
		assertEquals(expectedCheckingBalance, bank.getAccount("12345678").getBalance(), 0.01);

		// Process for the third month for Checking account (interest applied based on
		// type)
		processor.process("pass 1"); // Pass one more month, applies interest

		// Update expected balance after the third month for Checking account
		expectedCheckingBalance *= 1.01; // Apply 1% interest for the third month
		expectedCheckingBalance = Math.round(expectedCheckingBalance * 100.0) / 100.0; // Round to 2 decimal places

		// Assert balance after 3 months
		assertEquals(expectedCheckingBalance, bank.getAccount("12345678").getBalance(), 0.01);
	}

	@Test
	public void testSavingsAccountAPRForThreeMonths() {
		// Create Savings account with 6% APR (0.5% per month)
		bank.createSavingsAccount("87654321", 6.0); // Savings account with 6% APR (0.5% per month)

		// Deposit initial balance
		bank.getAccount("87654321").deposit(1000); // Savings deposit

		// Process 1 month for Savings account (interest applied based on type)
		processor.process("pass 1"); // Pass one month, applies interest

		// Calculate expected balance for Savings account after 1 month
		double expectedSavingsBalance = 1000.0 * 1.005; // 0.5% interest
		expectedSavingsBalance = Math.round(expectedSavingsBalance * 100.0) / 100.0; // Round to 2 decimal places

		// Assert balance after 1 month
		assertEquals(expectedSavingsBalance, bank.getAccount("87654321").getBalance(), 0.01);

		// Process for the second month for Savings account (interest applied based on
		// type)
		processor.process("pass 1"); // Pass one more month, applies interest

		// Update expected balance after the second month for Savings account
		expectedSavingsBalance *= 1.005; // Apply 0.5% interest for the second month
		expectedSavingsBalance = Math.round(expectedSavingsBalance * 100.0) / 100.0; // Round to 2 decimal places

		// Assert balance after 2 months
		assertEquals(expectedSavingsBalance, bank.getAccount("87654321").getBalance(), 0.01);

		// Process for the third month for Savings account (interest applied based on
		// type)
		processor.process("pass 1"); // Pass one more month, applies interest

		// Update expected balance after the third month for Savings account
		expectedSavingsBalance *= 1.005; // Apply 0.5% interest for the third month
		expectedSavingsBalance = Math.round(expectedSavingsBalance * 100.0) / 100.0; // Round to 2 decimal places

		// Assert balance after 3 months
		assertEquals(expectedSavingsBalance, bank.getAccount("87654321").getBalance(), 0.01);
	}

	@Test
	public void testCDAccountAPRForThreeMonths() {
		bank.createCDAccount("11223344", 12.0, 1000); // CD account with 12% APR (compounded 4 times per month)

		double monthlyRate = 12.0 / 12 / 100; // 12% APR, divided by 12 months and 4 compounding periods

		processor.process("pass 1"); // Pass one month

		double expectedCDBalance = 1000.0;
		for (int i = 0; i < 4; i++) {
			expectedCDBalance *= (1 + monthlyRate);
		}

		assertEquals(expectedCDBalance, bank.getAccount("11223344").getBalance(), 0.01);

		processor.process("pass 1");

		for (int i = 0; i < 4; i++) {
			expectedCDBalance *= (1 + monthlyRate);
		}

		assertEquals(expectedCDBalance, bank.getAccount("11223344").getBalance(), 0.01);

		processor.process("pass 1");

		for (int i = 0; i < 4; i++) {
			expectedCDBalance *= (1 + monthlyRate);
		}

		assertEquals(expectedCDBalance, bank.getAccount("11223344").getBalance(), 0.01);
	}

	@Test
	public void testAccruingAPRForMultipleAccountsIncludingCD() {
		// Create Checking account with 12% APR (1% per month)
		bank.createCheckingAccount("12345678", 12.0); // Checking account with 12% APR (1% per month)
		bank.createSavingsAccount("87654321", 6.0); // Savings account with 6% APR (0.5% per month)
		bank.createCDAccount("11223344", 12.0, 1000);

		bank.getAccount("12345678").deposit(1000); // Checking deposit
		bank.getAccount("87654321").deposit(1000); // Savings deposit

		processor.process("pass 1"); // Pass one month, applies interest

		double expectedCheckingBalance = 1000.0 * 1.01; // 1% interest
		expectedCheckingBalance = Math.round(expectedCheckingBalance * 100.0) / 100.0; // Round to 2 decimal places

		double expectedSavingsBalance = 1000.0 * 1.005; // 0.5% interest
		expectedSavingsBalance = Math.round(expectedSavingsBalance * 100.0) / 100.0; // Round to 2 decimal places

		double expectedCDBalance = 1000.0;
		double monthlyRate = 12.0 / 12 / 100;
		for (int i = 0; i < 4; i++) {
			expectedCDBalance *= (1 + monthlyRate); // Apply interest 4 times in the month
		}

		assertEquals(expectedCheckingBalance, bank.getAccount("12345678").getBalance(), 0.01);
		assertEquals(expectedSavingsBalance, bank.getAccount("87654321").getBalance(), 0.01);
		assertEquals(expectedCDBalance, bank.getAccount("11223344").getBalance(), 0.01);

		processor.process("pass 1"); // Pass one more month, applies interest

		// Update expected balance after the second month for Savings account
		expectedSavingsBalance *= 1.005; // Apply 0.5% interest for the second month
		expectedSavingsBalance = Math.round(expectedSavingsBalance * 100.0) / 100.0; // Round to 2 decimal places

		// Update expected balance after the second month for Checking account
		expectedCheckingBalance *= 1.01; // Apply 1% interest for the second month
		expectedCheckingBalance = Math.round(expectedCheckingBalance * 100.0) / 100.0; // Round to 2 decimal places

		// Update expected balance after the second month for CD
		for (int i = 0; i < 4; i++) {
			expectedCDBalance *= (1 + monthlyRate); // Apply interest 4 times in the second month
		}

		assertEquals(expectedCDBalance, bank.getAccount("11223344").getBalance(), 0.01);
		assertEquals(expectedCheckingBalance, bank.getAccount("12345678").getBalance(), 0.01);
		assertEquals(expectedSavingsBalance, bank.getAccount("87654321").getBalance(), 0.01);

		// Process for the third month for Checking account (interest applied based on
		processor.process("pass 1"); // Pass one more month, applies interest

		// Update expected balance after the third month for Checking account
		expectedCheckingBalance *= 1.01; // Apply 1% interest for the third month
		expectedCheckingBalance = Math.round(expectedCheckingBalance * 100.0) / 100.0; // Round to 2 decimal places

		expectedSavingsBalance *= 1.005; // Apply 0.5% interest for the third month
		expectedSavingsBalance = Math.round(expectedSavingsBalance * 100.0) / 100.0; // Round to 2 decimal places

		for (int i = 0; i < 4; i++) {
			expectedCDBalance *= (1 + monthlyRate); // Apply interest 4 times in the third month
		}

		// Assert balance after 3 months
		assertEquals(expectedCDBalance, bank.getAccount("11223344").getBalance(), 0.01);
		assertEquals(expectedSavingsBalance, bank.getAccount("87654321").getBalance(), 0.01);
		assertEquals(expectedCheckingBalance, bank.getAccount("12345678").getBalance(), 0.01);

	}

	@Test
	public void testProcessCDAccountWithAPR() {
		bank.createCDAccount("34567890", 2.1, 2000.0);
		processor.process("pass 1");

		Account account = bank.getAccount("34567890");
		assertNotNull(account, "CD Account should exist.");

		// Calculate the expected balance after applying APR for 1 month
		double expectedBalance = 2000.0;
		double monthlyRate = 2.1 / 12 / 100;

		// Apply APR 4 times (compounding monthly)
		for (int i = 0; i < 4; i++) {
			double interest = expectedBalance * monthlyRate;
			expectedBalance += interest; // Add the interest to the balance
		}

		// Assert that the actual balance matches the expected balance with a tolerance
		// of 0.001
		assertEquals(expectedBalance, account.getBalance(), 0.001,
				"CD Account balance should reflect the interest after 1 month.");
	}

}
