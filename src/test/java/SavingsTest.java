import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsTest {
	private Savings savingsAccount;

	@BeforeEach
	public void setUp() {
		savingsAccount = new Savings("88723455", 1.2);
	}

	@Test
	public void initial_balance_savings() {
		assertEquals(0.0, savingsAccount.getBalance());
	}

	@Test
	public void savingsAccount_APR_given() {
		assertEquals(1.2, savingsAccount.getApr());
	}

	@Test
	public void deposit_In_savings_account() {
		savingsAccount.deposit(100.0);
		assertEquals(100.0, savingsAccount.getBalance());
	}

	@Test
	public void withdraw_From_savings_account() {
		savingsAccount.deposit(100.0);
		savingsAccount.withdraw(50.0);
		assertEquals(50.0, savingsAccount.getBalance());
	}

	@Test
	public void savings_withdraw_cannot_go_below_zero() {
		savingsAccount.withdraw(100.0);
		assertEquals(0.0, savingsAccount.getBalance());
	}

	@Test
	public void deposit_twice_in_savings_account() {
		savingsAccount.deposit(100.0);
		savingsAccount.deposit(50.0);
		assertEquals(150.0, savingsAccount.getBalance());
	}

	@Test
	public void withdraw_twice_in_savings_account() {
		savingsAccount.deposit(200.0);
		savingsAccount.withdraw(50.0);
		savingsAccount.withdraw(100.0);
		assertEquals(50.0, savingsAccount.getBalance());
	}
}
