package com.svetanis.concurrency.diningphilosophers.deadlock;

import java.util.ArrayList;
import java.util.List;

// in the famous dining philosophers problem

// a bunch of philosophers are sitting around
// a circular table with one chopstick between
// each of them. a philosopher needs both chopsticks
// to eat, and always picks up the left chopstick 
// before the right one. a deadlock could potentially
// occur if all the philosophers reached for the left 
// chopstick at the same time. using threads and locks,
// implement a simulation of the dining philosophers
// problem that prevents deadlocks.
//
// THIS package is the version that CAN deadlock, on purpose. Every philosopher takes the left
// chopstick, then the right. If all of them hold their left at the same moment, each waits forever
// for a right chopstick its neighbour holds: a cycle of waits. A run can finish many times before
// the timing lines up, which is what makes the bug easy to miss. deadlockfree takes the
// lower-numbered chopstick first, so no cycle can form; allornothing uses tryLock and puts the
// left one back when the right one is busy.

public final class DiningPhilosophers {

	private static int SIZE = 3;

	public static int leftOf(int i) {
		return i;
	}

	public static int rightOf(int i) {
		return (i + 1) % SIZE;
	}

	private static List<Philosopher> philosophers(List<Chopstick> chopsticks) {
		List<Philosopher> list = new ArrayList<>();
		for (int i = 0; i < SIZE; ++i) {
			Chopstick left = chopsticks.get(leftOf(i));
			Chopstick right = chopsticks.get(rightOf(i));
			list.add(new Philosopher(i, left, right));
		}
		return list;
	}

	private static List<Chopstick> chopsticks() {
		List<Chopstick> list = new ArrayList<>();
		for (int i = 0; i < SIZE + 1; ++i) {
			list.add(new Chopstick());
		}
		return list;
	}

	private static void diningPhilosophers() {
		List<Chopstick> chopsticks = chopsticks();
		List<Philosopher> philosophers = philosophers(chopsticks);
		for (int i = 0; i < SIZE; ++i) {
			System.out.println("philosopher " + i + " started");
			philosophers.get(i).start();
		}
	}

	public static void main(String[] args) {
		diningPhilosophers();
	}
}