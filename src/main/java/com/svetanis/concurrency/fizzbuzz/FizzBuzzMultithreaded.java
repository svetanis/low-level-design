package com.svetanis.concurrency.fizzbuzz;

import static com.google.common.collect.Lists.transform;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

public final class FizzBuzzMultithreaded {

	public static void main(String[] args) {
		int n = 100;
		List<Runnable> tasks = tasks(n);
		List<Thread> threads = transform(tasks, t -> new Thread(t));
		for (Thread thread : threads) {
			thread.start();
		}
	}

	private static ImmutableList<Runnable> tasks(int n) {
		Predicate<Integer> fbp = i -> i % 3 == 0 && i % 5 == 0;
		Predicate<Integer> fp = i -> i % 3 == 0 && i % 5 != 0;
		Predicate<Integer> bp = i -> i % 3 != 0 && i % 5 == 0;
		Predicate<Integer> ip = i -> i % 3 != 0 && i % 5 != 0;
		List<Runnable> list = new ArrayList<>();
		list.add(new FizzBuzz(n, fbp, i -> "FizzBuzz"));
		list.add(new FizzBuzz(n, fp, i -> "Fizz"));
		list.add(new FizzBuzz(n, bp, i -> "Buzz"));
		list.add(new FizzBuzz(n, ip, i -> i + ""));
		return newList(list);
	}

}
