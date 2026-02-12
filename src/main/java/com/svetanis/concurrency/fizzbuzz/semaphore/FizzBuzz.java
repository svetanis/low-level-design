package com.svetanis.concurrency.fizzbuzz.semaphore;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public final class FizzBuzz {

	public FizzBuzz(int n) {
		this.n = n;
	}

	private final int n;

	private Semaphore fs = new Semaphore(0);
	private Semaphore bs = new Semaphore(0);
	private Semaphore fbs = new Semaphore(0);
	private Semaphore ns = new Semaphore(1);

	// printFizz.run() outputs "fizz".
	public void fizz(Runnable printFizz) throws InterruptedException {
		for (int i = 3; i <= n; i += 3) {
			if (i % 5 != 0) {
				fs.acquire();
				printFizz.run();
				ns.release();
			}
		}
	}

	// printBuzz.run() outputs "buzz".
	public void buzz(Runnable printBuzz) throws InterruptedException {
		for (int i = 5; i <= n; i += 5) {
			if (i % 3 != 0) {
				bs.acquire();
				printBuzz.run();
				ns.release();
			}
		}
	}

	// printFizzBuzz.run() outputs "fizzbuzz".
	public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
		for (int i = 15; i <= n; i += 15) {
			fbs.acquire();
			printFizzBuzz.run();
			ns.release();
		}
	}

	// printNumber.accept(x) outputs "x", where x is an integer.
	public void number(IntConsumer printNumber) throws InterruptedException {
		for (int i = 1; i <= n; i++) {
			ns.acquire();
			if (i % 3 == 0 && i % 5 == 0) {
				fbs.release();
			} else if (i % 3 == 0) {
				fs.release();
			} else if (i % 5 == 0) {
				bs.release();
			} else {
				printNumber.accept(i);
				ns.release();
			}
		}
	}
}