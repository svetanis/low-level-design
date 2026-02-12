package com.svetanis.concurrency.lock;

// Lock for general read-modify-write

public final class BankAccount {

	private int balance = 0;
	private final Object balanceLock = new Object();

	public void deposit(int amount) {
		synchronized (balanceLock) {
			balance = balance + amount;
		}
	}

	public void withdraw(int amount) {
		synchronized (balanceLock) {
			balance = balance - amount;
		}
	}
}
