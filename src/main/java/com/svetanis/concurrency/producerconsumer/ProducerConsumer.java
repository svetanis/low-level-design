package com.svetanis.concurrency.producerconsumer;

import static com.google.common.collect.Lists.transform;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.ImmutableList;

public final class ProducerConsumer {

	public static void main(String[] args) {
		MessageQueue queue = new MessageQueue();
		List<Producer> producers = producers(queue);
		List<Consumer> consumers = consumers(queue);
		List<Runnable> ptasks = transform(producers, p -> task(p));
		List<Runnable> ctasks = transform(consumers, c -> task(c));
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for (Runnable ptask : ptasks) {
			executor.submit(ptask);
		}
		for (Runnable ctask : ctasks) {
			executor.submit(ctask);
		}
		executor.shutdown();
	}

	private static Runnable task(Consumer consumer) {
		Runnable task = () -> {
			while (true) {
				try {
					consumer.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		return task;
	}

	private static Runnable task(Producer producer) {
		Runnable task = () -> {
			while (true) {
				try {
					producer.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		return task;
	}

	private static ImmutableList<Producer> producers(MessageQueue queue) {
		List<Producer> list = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			String name = "Producer[" + i + "]";
			list.add(new Producer(name, queue));
		}
		return newList(list);
	}

	private static ImmutableList<Consumer> consumers(MessageQueue queue) {
		List<Consumer> list = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			String name = "Consumer[" + i + "]";
			list.add(new Consumer(name, queue));
		}
		return newList(list);
	}
}
