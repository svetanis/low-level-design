package com.svetanis.concurrency.loadleveling;

import static java.lang.String.format;

public final class TaskGenerator implements Runnable {

	private final static String TEMPLATE = "pool[%s] worker[%s]";
	private static final String MSG = "%s generated message[%s]";

	public TaskGenerator(int count, int poolId, int workerId, MessageQueue queue) {
		this.count = count;
		this.poolId = poolId;
		this.workerId = workerId;
		this.queue = queue;
	}

	private final int count;
	private final int poolId;
	private final int workerId;
	private final MessageQueue queue;

	// put blocks when the buffer is full, which is the levelling this class exists to show.
	@Override
	public void run() {
		String task = format(TEMPLATE, poolId, workerId);
		for (int i = 0; i < count; i++) {
			try {
				queue.put(new Message(i, task));
			} catch (InterruptedException e) {
				// restore the flag the throw cleared, and stop generating
				Thread.currentThread().interrupt();
				return;
			}
			System.out.println(format(MSG, task, i));
		}
	}
}