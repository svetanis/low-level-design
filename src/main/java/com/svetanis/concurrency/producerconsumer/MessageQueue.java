package com.svetanis.concurrency.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class MessageQueue {

	public MessageQueue() {
		this.queue = new LinkedBlockingQueue<>(5);
	}

	private final BlockingQueue<Message> queue;

	public void put(Message message) throws InterruptedException {
		queue.put(message);
	}

	public Message get() throws InterruptedException {
		return queue.take();
	}
}