package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for the abstract banking.Account class. Since banking.Account is
 * abstract, we'll use a concrete subclass for testing.
 */
public class AccountTest {

	@Test
	public void testAccountCreation() {
		Account account = new TestAccount("A1", 0.02);

		assertEquals("A1", account.getAccountId());
		assertEquals(0.02, account.getApr(), 0.01);
		assertEquals(0.0, account.getBalance(), 0.01);
	}

	@Test
	public void testDepositValidAmount() {
		Account account = new TestAccount("A1", 0.02);

		account.deposit(500);
		assertEquals(500, account.getBalance(), 0.01);

		account.deposit(300);
		assertEquals(800, account.getBalance(), 0.01);
	}

	@Test
	public void testDepositZeroOrNegativeAmount() {
		Account account = new TestAccount("A1", 0.02);

		account.deposit(0);
		assertEquals(0.0, account.getBalance(), 0.01);

		account.deposit(-100);
		assertEquals(0.0, account.getBalance(), 0.01);
	}

	@Test
	public void testWithdrawValidAmount() {
		Account account = new TestAccount("A1", 0.02);

		account.deposit(1000);
		account.withdraw(400);
		assertEquals(600, account.getBalance(), 0.01);

		account.withdraw(600);
		assertEquals(0.0, account.getBalance(), 0.01);
	}

	@Test
	public void testWithdrawExceedingBalance() {
		Account account = new TestAccount("A1", 0.02);

		account.deposit(500);
		account.withdraw(600);
		assertEquals(0.0, account.getBalance(), 0.01);
	}

	@Test
	public void testWithdrawZeroOrNegativeAmount() {
		Account account = new TestAccount("A1", 0.02);

		account.deposit(500);
		account.withdraw(0);
		assertEquals(500, account.getBalance(), 0.01);

		account.withdraw(-100);
		assertEquals(500, account.getBalance(), 0.01);
	}

	@Test
	public void testSetBalance() {
		Account account = new TestAccount("A1", 0.02);

		account.setBalance(1000);
		assertEquals(1000, account.getBalance(), 0.01);

		account.setBalance(500);
		assertEquals(500, account.getBalance(), 0.01);
	}

	// A concrete subclass of banking.Account for testing purposes
	private static class TestAccount extends Account {
		public TestAccount(String accountId, double apr) {
			super(accountId, apr);
		}

		@Override
		public String getType() {
			return "testing";
		}
	}

}
