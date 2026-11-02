package com.svetanis.concurrency.producerconsumer;

import static com.svetanis.java.base.collect.Lists.newList;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.ImmutableList;

// Producers and consumers sharing a bounded BlockingQueue.
//
// The workers loop until they are interrupted, and stopping them is the part
// worth reading: an InterruptedException clears the interrupt flag on the way
// out, so a catch that does not restore it leaves the thread looking
// un-interrupted and the loop runs on. shutdown() and shutdownNow() are then
// both powerless and the pool stays saturated for the life of the JVM.
public final class ProducerConsumer {

	private static final int POOL = 5;
	private static final int PRODUCERS = 2;
	private static final int CONSUMERS = 3;
	private static final long RUN_MILLIS = 2_000;
	private static final long SHUTDOWN_SECONDS = 5;
	private static final String STOPPED = "all workers stopped within %d seconds of shutdownNow: %s%n";

	public static void main(String[] args) throws InterruptedException {
		MessageQueue queue = new MessageQueue();
		ExecutorService executor = Executors.newFixedThreadPool(POOL);
		for (Producer producer : producers(queue)) {
			executor.submit(() -> run(producer));
		}
		for (Consumer consumer : consumers(queue)) {
			executor.submit(() -> run(consumer));
		}
		Thread.sleep(RUN_MILLIS);

		// shutdown() only refuses new tasks; it never interrupts a running one,
		// so on its own it cannot stop a worker that loops. shutdownNow() sends
		// the interrupt, and the interrupt lands only because run(..) restores
		// the flag and leaves the loop
		executor.shutdownNow();
		boolean stopped = executor.awaitTermination(SHUTDOWN_SECONDS, SECONDS);
		System.out.printf(STOPPED, SHUTDOWN_SECONDS, stopped);
	}

	private static void run(Producer producer) {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				producer.produce();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}
	}

	private static void run(Consumer consumer) {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				consumer.consume();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}
	}

	private static ImmutableList<Producer> producers(MessageQueue queue) {
		List<Producer> list = new ArrayList<>();
		for (int i = 0; i < PRODUCERS; i++) {
			String name = "Producer[" + i + "]";
			list.add(new Producer(name, queue));
		}
		return newList(list);
	}

	private static ImmutableList<Consumer> consumers(MessageQueue queue) {
		List<Consumer> list = new ArrayList<>();
		for (int i = 0; i < CONSUMERS; i++) {
			String name = "Consumer[" + i + "]";
			list.add(new Consumer(name, queue));
		}
		return newList(list);
	}
}
