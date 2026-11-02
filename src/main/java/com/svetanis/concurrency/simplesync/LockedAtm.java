package com.svetanis.concurrency.simplesync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class LockedAtm {

	private int balance;
	// final so the reference is safely published: a thread that sees the object must see the
	// lock, and a null here would mean two callers taking no lock at all
	private final Lock lock;

	public LockedAtm(int balance) {
		this.balance = balance;
		this.lock = new ReentrantLock();
	}

	// unlock goes in a finally, NOT after the try block. putting it after only works while the
	// one call that can throw is a Thread.sleep whose exception is caught here -- interrupting
	// a withdrawal then still leaves the lock released for the next caller.
	// add one statement that throws past the catch and the lock is never released: the next
	// caller waits for it forever.
	public int withdraw(int value) {
		lock.lock();
		try {
			return apply(-value);
		} finally {
			lock.unlock();
		}
	}

	public int deposit(int value) {
		lock.lock();
		try {
			return apply(value);
		} finally {
			lock.unlock();
		}
	}

	// the sleeps are what make the race visible without them the interleaving is too fast to see
	private int apply(int delta) {
		int temp = balance;
		try {
			Thread.sleep(100);
			temp = temp + delta;
			Thread.sleep(100);
			balance = temp; // STORE: only now does the new balance exist
			return temp;
		} catch (InterruptedException e) {
			// restore the flag the throw cleared, so a caller above can still tell it was
			// interrupted. nothing was stored, so the answer is the balance as it stands -- not
			// temp, which may already hold the delta that was never written back
			Thread.currentThread().interrupt();
			return balance; // UNCHANGED: the caller can compare and see nothing happened
		}
	}
}
