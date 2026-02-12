package com.svetanis.concurrency.fizzbuzz;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public final class FizzBuzz implements Runnable {

	private static int COUNT = 1;
	private static final Object LOCK = new Object();

	public FizzBuzz(int max, Predicate<Integer> fbp, Function<Integer, String> fbf) {
		this.max = max;
		this.fbp = fbp;
		this.fbf = fbf;
	}

	private final int max;
	private final Predicate<Integer> fbp;
	private final Function<Integer, String> fbf;

	@Override
	public void run() {
		while (true) {
			synchronized (LOCK) {
				if (COUNT > max) {
					return;
				}
				if (fbp.apply(COUNT)) {
					System.out.println(fbf.apply(COUNT));
					COUNT++;
				}
			}
		}
	}
}