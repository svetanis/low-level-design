package com.svetanis.concurrency.diningphilosophers.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Chopstick {

	private final Lock lock;

	public Chopstick() {
		lock = new ReentrantLock();
	}

	// Acquires the lock
	public void pickUp() {
		lock.lock();
	}

	// Releases the lock
	public void putDown() {
		lock.unlock();
	}
}