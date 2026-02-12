package com.svetanis.concurrency.loadleveling;

import static java.lang.String.format;

import com.google.common.base.Optional;

public final class ConsumerExecutor implements Runnable {

	private static final String MSG = "consumed message[%s] submitted by %s";

	public ConsumerExecutor(MessageQueue queue) {
		this.queue = queue;
	}

	private final MessageQueue queue;

	@Override
	public void run() {
		while (true) {
			Optional<Message> message = queue.get();
			if (message.isPresent()) {
				int mid = message.get().getId();
				String task = message.get().getTask();
				System.out.println(format(MSG, mid, task));
			} else {
				System.out.println("Waiting for messages");
			}
		}

	}
}