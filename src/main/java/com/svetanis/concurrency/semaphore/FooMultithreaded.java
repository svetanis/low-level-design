package com.svetanis.concurrency.semaphore;

// 1114. Print In Order

public final class FooMultithreaded {

	public static void main(String[] args) {
		Foo foo = new Foo();
		Runnable first = () -> System.out.println("first");
		Runnable second = () -> System.out.println("second");
		Runnable third = () -> System.out.println("third");

		Thread ft = new Thread(() -> first(foo, first));
		Thread st = new Thread(() -> second(foo, second));
		Thread tt = new Thread(() -> third(foo, third));

		ft.start();
		st.start();
		tt.start();
	}

	private static void third(Foo foo, Runnable task) {
		try {
			foo.third(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void second(Foo foo, Runnable task) {
		try {
			foo.second(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void first(Foo foo, Runnable task) {
		try {
			foo.first(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
