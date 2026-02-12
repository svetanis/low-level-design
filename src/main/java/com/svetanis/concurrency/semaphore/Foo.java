package com.svetanis.concurrency.semaphore;

import java.util.concurrent.Semaphore;

// 1114. Print in Order

// The same instance of Foo will be
// passed to three different threads. 
// Thread A will call first(), 
// thread B will call second(), 
// and thread C will call third(). 

// Design a mechanism and modify the program 
// to ensure that second() is executed after first(), 
// and third() is executed after second().

public final class Foo {

	private final Semaphore fs = new Semaphore(1);
	private final Semaphore ss = new Semaphore(0);
	private final Semaphore ts = new Semaphore(0);

	public void first(Runnable first) throws InterruptedException {
		// wait for the first job's semaphore to be available
		fs.acquire();
		// printFirst.run() outputs "first". Do not change or remove this line.
		first.run();
		// release the second job semaphore, allowing second job to run
		ss.release();
	}

	public void second(Runnable second) throws InterruptedException {
		// wait for the second job's semaphore to be available
		ss.acquire();
		// printSecond.run() outputs "second". Do not change or remove this line.
		second.run();
		// release the third job semaphore, allowing third job to run
		ts.release();
	}

	public void third(Runnable third) throws InterruptedException {
		// wait for the third job's semaphore to be available
		ts.acquire();
		// printThird.run() outputs "third". Do not change or remove this line.
		third.run();
		// release the first job semaphore, allowing the cycle of jobs to be restarted
		fs.release();
	}
}