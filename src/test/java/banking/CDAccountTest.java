package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDAccountTest {
	private CDAccount cdAccount;

	@BeforeEach
	public void setUp() {
		cdAccount = new CDAccount("12987532", 1.5, 1000.0);
	}

	@Test
	public void initial_balance_cd_account() {
		assertEquals(1000.0, cdAccount.getBalance());
	}

	@Test
	public void cdAccount_APR_given() {
		assertEquals(1.5, cdAccount.getApr());
	}

	@Test
	public void deposit_In_cd_account() {
		cdAccount.deposit(200.0);
		assertEquals(1200.0, cdAccount.getBalance());
	}

	@Test
	public void withdraw_From_cd_account() {
		cdAccount.withdraw(500.0);
		assertEquals(500.0, cdAccount.getBalance());
	}

	@Test
	public void cd_withdraw_cannot_go_below_zero() {
		cdAccount.withdraw(1000.0);
		assertEquals(0.0, cdAccount.getBalance());
	}

	@Test
	public void deposit_twice_in_cd_account() {
		cdAccount.deposit(200.0);
		cdAccount.deposit(300.0);
		assertEquals(1500.0, cdAccount.getBalance());
	}

	@Test
	public void withdraw_twice_in_cd_account() {
		cdAccount.withdraw(300.0);
		cdAccount.withdraw(200.0);
		assertEquals(500.0, cdAccount.getBalance());
	}
}
