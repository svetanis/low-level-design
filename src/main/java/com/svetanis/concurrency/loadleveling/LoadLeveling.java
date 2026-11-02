package com.svetanis.concurrency.loadleveling;

import static com.svetanis.java.base.collect.Lists.newList;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.ImmutableList;

public final class LoadLeveling {

	private static final int POOL_SIZE = 5;
	private static final int DRAIN_SECONDS = 2;

	public static void main(String[] args) throws InterruptedException {
		MessageQueue queue = new MessageQueue();
		List<Runnable> tasks = tasks(queue);
		Runnable consumer = new ConsumerExecutor(queue);
		ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
		for (Runnable task : tasks) {
			executor.submit(task);
		}
		executor.submit(consumer);
		// the producers finish on their own; the consumer is waiting for messages that are
		// never coming, so it is stopped rather than waited for. shutdown() by itself does
		// not interrupt a running task, so this used to sit in take() forever - and a pool
		// thread is not a daemon, so the JVM did not exit either.
		executor.shutdown();
		if (!executor.awaitTermination(DRAIN_SECONDS, SECONDS)) {
			executor.shutdownNow();
			executor.awaitTermination(DRAIN_SECONDS, SECONDS);
		}
	}

	private static ImmutableList<Runnable> tasks(MessageQueue queue) {
		List<Runnable> list = new ArrayList<>();
		list.add(new TaskGenerator(5, 1, 1, queue));
		list.add(new TaskGenerator(1, 1, 2, queue));
		list.add(new TaskGenerator(2, 1, 3, queue));
		return newList(list);
	}
}
