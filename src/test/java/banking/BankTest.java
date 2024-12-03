package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
	}

	@Test
	public void bankStartsWithNoAccounts() {
		assertEquals(0, bank.getAccountsCount());
	}

	@Test
	public void createOneAccountInBank() {
		bank.createCheckingAccount("12345678", 1.5);
		assertEquals(1, bank.getAccountsCount());
	}

	@Test
	public void createTwoAccountsInBank() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.createSavingsAccount("87654321", 2.0);
		assertEquals(2, bank.getAccountsCount());
	}

	@Test
	public void retrieveAccountById() {
		bank.createCheckingAccount("12345678", 1.5);

		Account retrievedAccount = bank.getAccount("12345678");
		assertEquals(1.5, retrievedAccount.getApr());
	}

	@Test
	public void depositMoneyByIdInBank() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.getAccount("12345678").deposit(100.0);

		assertEquals(100.0, bank.getAccount("12345678").getBalance());
	}

	@Test
	public void withdrawMoneyByIdInBank() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.getAccount("12345678").deposit(100.0);
		bank.getAccount("12345678").withdraw(50.0);

		assertEquals(50.0, bank.getAccount("12345678").getBalance());
	}

	@Test
	public void depositTwiceThroughBank() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.getAccount("12345678").deposit(100.0);
		bank.getAccount("12345678").deposit(50.0);

		assertEquals(150.0, bank.getAccount("12345678").getBalance());
	}

	@Test
	public void withdrawTwiceThroughBank() {
		bank.createCheckingAccount("12345678", 1.5);
		bank.getAccount("12345678").deposit(200.0);
		bank.getAccount("12345678").withdraw(50.0);
		bank.getAccount("12345678").withdraw(100.0);

		assertEquals(50.0, bank.getAccount("12345678").getBalance());
	}
}
