package com.svetanis.concurrency.semaphore;

import java.util.function.IntConsumer;

// 1116. Print Zero Even Odd

public final class ZeroEvenOddMultithreaded {

	public static void main(String[] args) {
		int n = 5;
		ZeroEvenOdd zeo = new ZeroEvenOdd(n);
		IntConsumer ic = i -> System.out.print(i);

		Thread tz = new Thread(() -> zero(zeo, ic));
		Thread to = new Thread(() -> odd(zeo, ic));
		Thread te = new Thread(() -> even(zeo, ic));

		tz.start();
		to.start();
		te.start();
	}

	private static void zero(ZeroEvenOdd zeo, IntConsumer num) {
		try {
			zeo.zero(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void odd(ZeroEvenOdd zeo, IntConsumer num) {
		try {
			zeo.odd(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void even(ZeroEvenOdd zeo, IntConsumer num) {
		try {
			zeo.even(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
