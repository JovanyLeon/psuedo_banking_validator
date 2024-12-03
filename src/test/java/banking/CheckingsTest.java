package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingsTest {

	private Checkings checkingsAccount;

	@BeforeEach
	public void setUp() {
		checkingsAccount = new Checkings("12987532", 1.5);
	}

	@Test
	public void intial_balance_checkings() {
		assertEquals(0.0, checkingsAccount.getBalance());
	}

	@Test
	public void checkingAccount_APR_given() {
		assertEquals(1.5, checkingsAccount.getApr());
	}

	@Test
	public void deposit_In_checking_account() {
		checkingsAccount.deposit(100.0);
		assertEquals(100.0, checkingsAccount.getBalance());
	}

	@Test
	public void withdraw_From_checkingAccount() {
		checkingsAccount.deposit(100.0);
		checkingsAccount.withdraw(50.0);
		assertEquals(50.0, checkingsAccount.getBalance());
	}

	@Test
	public void checkings_withdraw_cannot_go_below_zero() {
		checkingsAccount.withdraw(100.0);
		assertEquals(0.0, checkingsAccount.getBalance());
	}

	@Test
	public void deposit_twice_in_checkingAccount() {
		checkingsAccount.deposit(100.0);
		checkingsAccount.deposit(50.0);
		assertEquals(150.0, checkingsAccount.getBalance());
	}

	@Test
	public void withdraw_twice_in_checking_account() {
		checkingsAccount.deposit(200.0);
		checkingsAccount.withdraw(50.0);
		checkingsAccount.withdraw(100.0);
		assertEquals(50.0, checkingsAccount.getBalance());
	}
}
