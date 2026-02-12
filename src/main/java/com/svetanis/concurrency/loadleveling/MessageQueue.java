package com.svetanis.concurrency.loadleveling;

import static com.google.common.base.Optional.absent;
import static com.svetanis.java.base.Optionals.present;

import java.util.LinkedList;
import java.util.Queue;

import com.google.common.base.Optional;

public final class MessageQueue {

	public MessageQueue() {
		this.queue = new LinkedList<>();
	}

	private final Queue<Message> queue;

	public void put(Message message) {
		queue.add(message);
	}

	public Optional<Message> get() {
		if (queue.isEmpty()) {
			return absent();
		}
		return present(queue.poll());
	}
}