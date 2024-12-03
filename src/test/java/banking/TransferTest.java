package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferTest {

	private Bank bank;
	private Transfer transfer;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		transfer = new Transfer(bank);
	}

	@Test
	public void testValidTransfer() {
		Bank bank = new Bank();
		bank.createCheckingAccount("A1", 0.01);
		bank.createSavingsAccount("A2", 0.02);

		Account account1 = bank.getAccount("A1");
		Account account2 = bank.getAccount("A2");

		account1.deposit(1000);
		account2.deposit(500);

		transfer = new Transfer(bank);

		// Valid transfer
		transfer.transfer("A1", "A2", 300);
		assertEquals(700, account1.getBalance(), 0.01);
		assertEquals(800, account2.getBalance(), 0.01);
	}

	@Test
	public void testTransferBetweenCheckingAccounts() {
		Bank bank = new Bank();
		bank.createCheckingAccount("C1", 0.01);
		bank.createCheckingAccount("C2", 0.01);

		Account fromAccount = bank.getAccount("C1");
		Account toAccount = bank.getAccount("C2");

		fromAccount.deposit(500);
		transfer = new Transfer(bank);

		transfer.transfer("C1", "C2", 200);

		assertEquals(300, fromAccount.getBalance(), 0.01);
		assertEquals(200, toAccount.getBalance(), 0.01);
	}

	@Test
	public void testValidTransferBetweenCheckingAndSavings() {
		bank.createCheckingAccount("C1", 0.01);
		bank.createSavingsAccount("S1", 0.02);

		Account account1 = bank.getAccount("C1");
		Account account2 = bank.getAccount("S1");

		account1.deposit(1000);

		transfer.transfer("C1", "S1", 300);
		assertEquals(700, account1.getBalance(), 0.01);
		assertEquals(300, account2.getBalance(), 0.01);
	}

	@Test
	public void testTransferBetweenSavingsAccounts() {
		bank.createSavingsAccount("S1", 0.02);
		bank.createSavingsAccount("S2", 0.02);

		Account account1 = bank.getAccount("S1");
		Account account2 = bank.getAccount("S2");

		account1.deposit(800);

		transfer.transfer("S1", "S2", 300);
		assertEquals(500, account1.getBalance(), 0.01);
		assertEquals(300, account2.getBalance(), 0.01);
	}

	@Test
	public void testCDAccountCannotBeInvolvedInTransfer() {
		bank.createCDAccount("CD1", 0.03, 1000);
		bank.createCheckingAccount("C1", 0.01);

		Account cdAccount = bank.getAccount("CD1");
		Account checkingAccount = bank.getAccount("C1");

		checkingAccount.deposit(500);

		// Attempt transfer from CD account
		assertThrows(IllegalArgumentException.class, () -> transfer.transfer("CD1", "C1", 100));
		assertEquals(1000, cdAccount.getBalance(), 0.01);
		assertEquals(500, checkingAccount.getBalance(), 0.01);

		// Attempt transfer to CD account
		assertThrows(IllegalArgumentException.class, () -> transfer.transfer("C1", "CD1", 100));
		assertEquals(1000, cdAccount.getBalance(), 0.01);
		assertEquals(500, checkingAccount.getBalance(), 0.01);
	}

	@Test
	public void testZeroAmountTransfer() {
		bank.createCheckingAccount("C1", 0.01);
		bank.createCheckingAccount("C2", 0.01);

		Account account1 = bank.getAccount("C1");
		Account account2 = bank.getAccount("C2");

		account1.deposit(500);

		transfer.transfer("C1", "C2", 0);
		assertEquals(500, account1.getBalance(), 0.01);
		assertEquals(0, account2.getBalance(), 0.01);
	}

}
