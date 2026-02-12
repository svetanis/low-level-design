package com.svetanis.concurrency.diningphilosophers.deadlockfree;

public class Philosopher extends Thread {

	private final int bites = 10;

	private final int index;
	private final Chopstick lower;
	private final Chopstick higher;

	// a philosopher can never hold the larger chopstick without
	// holding the smaller one. this prevents ability to have a cycle
	public Philosopher(int index, Chopstick lower, Chopstick higher) {
		this.index = index;
		boolean one = lower.getIndex() < higher.getIndex();
		this.lower = one ? lower : higher;
		this.higher = one ? higher : lower;
	}

	public void eat(int bite) {
		System.out.println("Philosopher " + index + ": start eating");
		pickUp();
		chew(bite);
		putDown();
		System.out.println("Philosopher " + index + ": done eating");
	}

	public void pickUp() {
		lower.pickUp();
		higher.pickUp();
	}

	public void putDown() {
		higher.putDown();
		lower.putDown();
	}

	public void chew(int bite) {
		System.out.println("Philosopher " + index + ": is eating bite " + bite);
		pause();
	}

	@Override
	public void run() {
		for (int bite = 0; bite < bites; ++bite) {
			eat(bite);
		}
	}

	public void pause() {
		try {
			int pause = 1000;
			Thread.sleep(pause);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
