package com.svetanis.concurrency.semaphore;

import java.util.concurrent.Semaphore;

// 1115. Print FooBar Alternately
// the same instance of FooBar will
// be passed to two different threads:
// thread A will call foo(), while
// thread B will call bar()
// modify the given program to 
// output "foobar" n times

public final class FooBar {

	public FooBar(int n) {
		this.n = n;
	}

	private int n;

	private final Semaphore fs = new Semaphore(1);
	private final Semaphore bs = new Semaphore(0);

	public void foo(Runnable printFoo) throws InterruptedException {
		for (int i = 0; i < n; i++) {
			// Acquire a permit before printing "foo", ensuring "foo" has the turn to print
			fs.acquire();
			// printFoo.run() outputs "foo". Do not change or remove this line.
			printFoo.run();
			// Release a permit for "bar" after "foo" is printed, allowing "bar" to print next
			bs.release();
		}
	}

	public void bar(Runnable printBar) throws InterruptedException {
		for (int i = 0; i < n; i++) {
			// Acquire a permit before printing "bar", ensuring "bar" has the turn to print
			bs.acquire();
			// printBar.run() outputs "bar". Do not change or remove this line.
			printBar.run();
			// Release a permit for "foo" after "bar" is printed, allowing "foo" to print next
			fs.release();
		}
	}
}