package com.svetanis.concurrency.lock;

// Lock for general read-modify-write.
//
// The lock makes each read-modify-write indivisible. It does not, and cannot,
// enforce what the balance is allowed to be -- a lock protects an invariant you
// actually state, it does not invent one. Without the check below, deposit(100)
// followed by withdraw(500) leaves the balance at -400 with the locking working
// perfectly the whole time.
public final class BankAccount {

	private static final String LINE = "%-28s -> %-5s balance = %d%n";
	private static final String BAD_AMOUNT = "amount must be positive, got %d";

	private int balance = 0;
	private final Object balanceLock = new Object();

	public void deposit(int amount) {
		checkPositive(amount); // REJECT: deposit(-500) would be a withdrawal with no balance check
		synchronized (balanceLock) {
			balance = balance + amount;
		}
	}

	// returns false when the account cannot cover the withdrawal, leaving the
	// balance untouched. the check has to be inside the lock: outside it, two
	// threads could both read a balance of 100 and both withdraw it
	public boolean withdraw(int amount) {
		checkPositive(amount); // REJECT: withdraw(-500) would pass the check below and add 500
		synchronized (balanceLock) {
			if (amount > balance) {
				return false;
			}
			balance = balance - amount;
			return true;
		}
	}

	public int balance() {
		synchronized (balanceLock) {
			return balance;
		}
	}

	// the second invariant the lock cannot state for us: an amount is a positive number of units.
	// it needs no lock -- it reads only the argument
	private static void checkPositive(int amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(BAD_AMOUNT.formatted(amount));
		}
	}

	public static void main(String[] args) {
		BankAccount account = new BankAccount();
		account.deposit(100);
		System.out.printf(LINE, "deposit(100)", "", account.balance());
		System.out.printf(LINE, "withdraw(500)", account.withdraw(500), account.balance());
		System.out.printf(LINE, "withdraw(100)", account.withdraw(100), account.balance());
		try {
			account.withdraw(-500);
		} catch (IllegalArgumentException e) {
			System.out.printf(LINE, "withdraw(-500)", "throw", account.balance());
		}
	}
}
