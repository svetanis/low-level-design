package com.svetanis.concurrency.producerconsumer;

import static java.lang.String.format;

public final class Consumer {

	private static final String MSG = "%s consume message %s produced by %s";

	public Consumer(String name, MessageQueue queue) {
		this.name = name;
		this.queue = queue;
	}

	private final String name;
	private final MessageQueue queue;

	public void consume() throws InterruptedException {
		// Retrieves and removes the head of this queue,
		// waiting if necessary until an element becomes available.
		Message message = queue.get();
		String formatted = format(MSG, name, message.getId(), message.getProducer());
		System.out.println(formatted);
	}
}