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