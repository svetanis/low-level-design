package com.svetanis.concurrency;

public final class ThreadExample extends Thread {

	private int count = 0;

	@Override
	public void run() {
		System.out.println("Thread starting.");
		try {
			while (count < 5) {
				Thread.sleep(500);
				count++;
				System.out.println("In Thread count=" + count);
			}
		} catch (InterruptedException e) {
			System.out.println("Thread interrupted.");
		}
		System.out.println("Thread terminating.");
	}

}