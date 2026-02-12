package com.svetanis.concurrency.loadleveling;

import static com.svetanis.java.base.collect.Lists.newList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.ImmutableList;

public final class LoadLeveling {

	public static void main(String[] args) {
		MessageQueue queue = new MessageQueue();
		List<Runnable> tasks = tasks(queue);
		Runnable consumer = new ConsumerExecutor(queue);
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for (Runnable task : tasks) {
			executor.submit(task);
		}
		executor.submit(consumer);
		executor.shutdown();
	}

	private static ImmutableList<Runnable> tasks(MessageQueue queue) {
		List<Runnable> list = new ArrayList<>();
		list.add(new TaskGenerator(5, 1, 1, queue));
		list.add(new TaskGenerator(1, 1, 2, queue));
		list.add(new TaskGenerator(2, 1, 3, queue));
		return newList(list);
	}
}
