package com.svetanis.concurrency.fizzbuzz;

import static com.google.common.collect.Lists.transform;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

public final class FizzBuzzMultithreaded {

	private static final String RUN = "--- run %d of %d, n=%d ---";

	// two runs, because one cannot see the bug this file used to have: the count lived in a
	// static field, so the first run left it past max and every later run in the same JVM
	// printed nothing at all. one run looked perfect the whole time.
	public static void main(String[] args) throws InterruptedException {
		int n = 15;
		int runs = 2;
		for (int run = 1; run <= runs; run++) {
			System.out.println(String.format(RUN, run, runs, n));
			runOnce(n);
		}
	}

	private static void runOnce(int n) throws InterruptedException {
		// copyOf, not the view itself: Lists.transform is lazy and builds a new Thread on
		// every walk, so start() and join() over the view would work on different objects
		// and the join would wait on threads that were never started.
		ImmutableList<Thread> threads = ImmutableList.copyOf(transform(tasks(n), Thread::new));
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
	}

	private static ImmutableList<Runnable> tasks(int n) {
		Predicate<Integer> fbp = i -> i % 3 == 0 && i % 5 == 0;
		Predicate<Integer> fp = i -> i % 3 == 0 && i % 5 != 0;
		Predicate<Integer> bp = i -> i % 3 != 0 && i % 5 == 0;
		Predicate<Integer> ip = i -> i % 3 != 0 && i % 5 != 0;
		// one counter per run, handed to all four tasks: that is what they share
		FizzBuzz.Counter counter = new FizzBuzz.Counter();
		List<Runnable> list = new ArrayList<>();
		list.add(new FizzBuzz(n, counter, fbp, i -> "FizzBuzz"));
		list.add(new FizzBuzz(n, counter, fp, i -> "Fizz"));
		list.add(new FizzBuzz(n, counter, bp, i -> "Buzz"));
		list.add(new FizzBuzz(n, counter, ip, i -> i + ""));
		return newList(list);
	}

}
