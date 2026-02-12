package com.svetanis.concurrency.executor;

import static com.google.common.collect.Lists.transform;
import static com.google.common.util.concurrent.Futures.getUnchecked;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class ExecutorExample {

	public static void main(String[] args) throws ExecutionException {
		int offset = 100;
		List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		List<Callable<Integer>> callables = transform(list, i -> task(i, offset));
		ExecutorService executor = Executors.newFixedThreadPool(3);
		List<Future<Integer>> futures = transform(callables, callable -> executor.submit(callable));
		List<Integer> transformed = transform(futures, future -> getUnchecked(future));
		for (int num : transformed) {
			System.out.println(num);
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
