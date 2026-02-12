package com.svetanis.concurrency.diningphilosophers.deadlockfree;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Chopstick {

	private final int index;
	private final Lock lock;

	public Chopstick(int index) {
		this.index = index;
		this.lock = new ReentrantLock();
	}

	// Acquires the lock only if it is
	// free at the time of invocation
	public void pickUp() {
		lock.lock();
	}

	// Releases the lock
	public void putDown() {
		lock.unlock();
	}

	public int getIndex() {
		return index;
	}

}