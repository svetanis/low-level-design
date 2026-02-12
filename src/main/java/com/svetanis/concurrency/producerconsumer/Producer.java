package com.svetanis.concurrency.producerconsumer;

import java.security.SecureRandom;

public final class Producer {

	private static final SecureRandom RAND = new SecureRandom();

	public Producer(String name, MessageQueue queue) {
		this.name = name;
		this.queue = queue;
	}

	private final String name;
	private final MessageQueue queue;
	private int count = 1;

	public void produce() throws InterruptedException {
		// Inserts the specified element into this queue,
		// waiting if necessary for space to become available.
		Message message = new Message(count++, name);
		queue.put(message);
		Thread.sleep(RAND.nextInt(2000));
	}
}