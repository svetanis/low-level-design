package com.svetanis.concurrency;

public final class RunnableThreadExample implements Runnable {

	private int count = 0;

	@Override
	public void run() {
		System.out.println("RunnableThread starting.");
		try {
			while (count < 5) {
				Thread.sleep(500);
				count++;
				System.out.println("In RunnableThread count=" + count);
			}
		} catch (InterruptedException e) {
			System.out.println("RunnableThread interrupted.");
		}
		System.out.println("RunnableThread terminating.");
	}
}