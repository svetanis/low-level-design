package com.svetanis.concurrency.diningphilosophers.deadlockfree.allornothing;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Chopstick {

	private final Lock lock;

	public Chopstick() {
		this.lock = new ReentrantLock();
	}

	// Acquires the lock only if it is
	// free at the time of invocation
	public boolean pickUp() {
		return lock.tryLock();
	}

	// Releases the lock
	public void putDown() {
		lock.unlock();
	}
}