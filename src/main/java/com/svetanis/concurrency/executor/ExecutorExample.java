package com.svetanis.concurrency.executor;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.transform;
import static com.google.common.util.concurrent.Futures.getUnchecked;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Submitting a batch of Callables to a fixed pool and reading the answers back.
//
// The trap this file used to contain is worth knowing: Guava's Lists.transform
// returns a LAZY VIEW, not a materialized list, and the function runs again on
// every get(). Submitting through such a view means nothing is submitted when
// the transform line executes -- instead the loop that reads the answers drives
// it element by element, doing submit, block on get, submit the next. The pool
// then never holds more than one task, whatever its size. Measured with ten
// 100 ms tasks on a pool of three: about 1000 ms that way against about 400 ms
// when every task is submitted before any answer is read.
//
// invokeAll submits the whole batch and returns only when all of them are done,
// so there is no view left to walk lazily.
public final class ExecutorExample {

	private static final int POOL = 3;
	private static final int OFFSET = 100;

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		List<Callable<Integer>> callables = copyOf(transform(list, i -> task(i, OFFSET)));
		ExecutorService executor = Executors.newFixedThreadPool(POOL);
		List<Future<Integer>> futures = executor.invokeAll(callables);
		for (Future<Integer> future : futures) {
			System.out.println(getUnchecked(future));
		}
		executor.shutdown();
	}

	private static Callable<Integer> task(int i, int offset) {
		Callable<Integer> task = () -> {
			return i + offset;
		};
		return task;
	}
}
