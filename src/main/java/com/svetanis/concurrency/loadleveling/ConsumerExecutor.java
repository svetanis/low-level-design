package com.svetanis.concurrency.loadleveling;

import static java.lang.String.format;

public final class ConsumerExecutor implements Runnable {

	private static final String MSG = "consumed message[%s] submitted by %s";

	public ConsumerExecutor(MessageQueue queue) {
		this.queue = queue;
	}

	private final MessageQueue queue;

	// waits in take() rather than asking an empty queue over and over. the poll-and-print
	// version ran roughly eight million empty polls a second on one thread and printed a line
	// for every one of them, which is work the queue can do by blocking instead.
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				consume(queue.get());
			} catch (InterruptedException e) {
				// restore the flag the throw cleared, so the loop condition can see it and
				// so anything above this task still knows the thread was interrupted
				Thread.currentThread().interrupt();
			}
		}
	}

	private static void consume(Message message) {
		System.out.println(format(MSG, message.getId(), message.getTask()));
	}
}
