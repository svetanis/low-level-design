package com.svetanis.concurrency.semaphore;

import java.util.concurrent.Semaphore;

// 1117. Building H2O

public final class Water {

	private final Semaphore sh = new Semaphore(2);
	private final Semaphore so = new Semaphore(0);

	public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
		// Acquire a permit for releasing a hydrogen atom
		sh.acquire();
		// releaseHydrogen.run() outputs "H". Do not change or remove this line.
		releaseHydrogen.run();
		// Release a permit for oxygen, signaling that one H has been released
		so.release();
	}

	public void oxygen(Runnable releaseOxygen) throws InterruptedException {
		// Acquire two permits for releasing an oxygen atom as we
		// need two hydrogen atoms before releasing one oxygen atom
		so.acquire(2);
		// releaseOxygen.run() outputs "O". Do not change or remove this line.
		releaseOxygen.run();
		// Release two permits for hydrogen, allowing the release of two hydrogen atoms
		sh.release(2);
	}
}