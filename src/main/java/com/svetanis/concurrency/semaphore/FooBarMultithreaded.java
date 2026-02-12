package com.svetanis.concurrency.semaphore;

// 1115. Print FooBar Alternately

public final class FooBarMultithreaded {

	public static void main(String[] args) {
		FooBar fb = new FooBar(2);
		Runnable foo = () -> System.out.print("foo");
		Runnable bar = () -> System.out.print("bar");

		Thread ft = new Thread(() -> foo(fb, foo));
		Thread bt = new Thread(() -> bar(fb, bar));

		ft.start();
		bt.start();
	}

	private static void foo(FooBar fb, Runnable task) {
		try {
			fb.foo(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void bar(FooBar fb, Runnable task) {
		try {
			fb.bar(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
