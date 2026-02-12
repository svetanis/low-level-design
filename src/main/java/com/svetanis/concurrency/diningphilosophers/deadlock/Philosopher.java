package com.svetanis.concurrency.diningphilosophers.deadlock;

public final class Philosopher extends Thread {

	private final int bites = 10;
	private final int index;
	private final Chopstick left;
	private final Chopstick right;

	public Philosopher(int index, Chopstick left, Chopstick right) {
		this.index = index;
		this.left = left;
		this.right = right;
	}

	public void eat(int bite) {
		System.out.println("Philosopher " + index + ": start eating");
		pickUp();
		chew(bite);
		putDown();
		System.out.println("Philosopher " + index + ": done eating");
	}

	public void pickUp() {
		left.pickUp();
		right.pickUp();
	}

	public void putDown() {
		left.putDown();
		right.putDown();
	}

	public void chew(int bite) {
		System.out.println("Philosopher " + index + ": is eating bite " + bite);
	}

	@Override
	public void run() {
		for (int bite = 0; bite < bites; bite++) {
			eat(bite);
		}
	}
}
