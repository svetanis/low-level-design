package com.svetanis.concurrency.diningphilosophers.deadlockfree.allornothing;

// one potential issue:
// if all the philosophers were perfectly synchronized,
// they could simultaneously pick up their left chopstick,
// be unable to pick up the right one, and then put back
// down the left one 
public class Philosopher extends Thread {

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
		if (pickUp()) {
			chew(bite);
			putDown();
			System.out.println("Philosopher " + index + ": done eating");
		} else {
			System.out.println("Philosopher " + index + ": gave up on eating");
		}
	}

	// a philosopher will put down his left chopstick
	// if he is unable to pick up the right one
	public boolean pickUp() {
		pause();
		if (!left.pickUp()) {
			return false;
		}
		pause();
		if (!right.pickUp()) {
			left.putDown();
			return false;
		}
		pause();
		return true;
	}

	public void putDown() {
		left.putDown();
		right.putDown();
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
