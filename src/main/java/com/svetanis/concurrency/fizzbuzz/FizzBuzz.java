package com.svetanis.concurrency.fizzbuzz;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public final class FizzBuzz implements Runnable {

	public FizzBuzz(int max, Counter counter, Predicate<Integer> fbp, Function<Integer, String> fbf) {
		this.max = max;
		this.counter = counter;
		this.fbp = fbp;
		this.fbf = fbf;
	}

	private final int max;
	private final Counter counter;
	private final Predicate<Integer> fbp;
	private final Function<Integer, String> fbf;

	// the four tasks of one run share one Counter, and printing in order is what that sharing
	// buys. it is also the object they lock on, so the count cannot be advanced by a task that
	// is not holding it. a static field shared it across runs as well, which left the second
	// run in a JVM with a count already past max and nothing to print. the sibling in
	// semaphore/ hands one instance to the four threads for the same reason.
	public static final class Counter {

		private int value = 1;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (counter) {
				if (counter.value > max) {
					return;
				}
				if (fbp.apply(counter.value)) {
					System.out.println(fbf.apply(counter.value));
					counter.value++;
				}
			}
		}
	}
}
