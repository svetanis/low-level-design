package com.svetanis.concurrency.loadleveling;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class MessageQueue {

	// bounded on purpose: this is the load-levelling buffer, so a producer that outruns the
	// consumer is meant to wait rather than let the backlog grow without limit. the sibling
	// in producerconsumer/ uses the same size for the same reason.
	private static final int CAPACITY = 5;

	public MessageQueue() {
		this.queue = new LinkedBlockingQueue<>(CAPACITY);
	}

	private final BlockingQueue<Message> queue;

	public void put(Message message) throws InterruptedException {
		queue.put(message);
	}

	// blocks until a message is available, so there is nothing to report as absent and no
	// reason for a caller to poll. a plain LinkedList here was not thread-safe: racing adds
	// left the size counter above the real element count, so isEmpty() said false while the
	// chain was exhausted and the Optional built from a null poll() threw instead.
	public Message get() throws InterruptedException {
		return queue.take();
	}
}
