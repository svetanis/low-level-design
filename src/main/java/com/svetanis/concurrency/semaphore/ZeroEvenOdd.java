package com.svetanis.concurrency.semaphore;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

// 1116. Print Zero Even Odd

public final class ZeroEvenOdd {

	private final Semaphore sz = new Semaphore(1);
	private final Semaphore so = new Semaphore(0);
	private final Semaphore se = new Semaphore(0);

	public ZeroEvenOdd(int n) {
		this.n = n;
	}

	private final int n;

	// printNumber.accept(x) outputs "x", where x is an integer.
	public void zero(IntConsumer printNumber) throws InterruptedException {
		for (int i = 1; i <= n; i++) {
			// Acquire the semaphore before printing 0.
			sz.acquire();
			printNumber.accept(0);
			// Determine whether to release the odd or even
			// semaphore based on the current iteration.
			if (i % 2 == 0) {
				se.release();
			} else {
				so.release();
			}
		}
	}

	public void even(IntConsumer printNumber) throws InterruptedException {
		for (int i = 2; i <= n; i += 2) {
			// Wait for the even semaphore to be released.
			se.acquire();
			printNumber.accept(i);
			// Release the zero semaphore after printing an even number.
			sz.release();
		}
	}

	public void odd(IntConsumer printNumber) throws InterruptedException {
		for (int i = 1; i <= n; i += 2) {
			// Wait for the odd semaphore to be released.
			so.acquire();
			printNumber.accept(i);
			// Release the zero semaphore after printing an odd number.
			sz.release();
		}
	}
}