package com.svetanis.concurrency.semaphore;

// 1117. Building H2O

public final class WaterMultithreaded {

	public static void main(String[] args) {
		Water h2o = new Water();
		Runnable h = () -> System.out.println("H");
		Runnable o = () -> System.out.println("O");

		Thread th = new Thread(() -> hydrogen(h2o, h));
		Thread to = new Thread(() -> oxygen(h2o, o));

		th.start();
		to.start();
	}

	private static void hydrogen(Water h2o, Runnable task) {
		try {
			h2o.hydrogen(task);
			h2o.hydrogen(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void oxygen(Water h2o, Runnable task) {
		try {
			h2o.oxygen(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
