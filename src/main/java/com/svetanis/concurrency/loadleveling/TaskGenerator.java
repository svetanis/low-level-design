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

	@Override
	public void run() {
		for (int i = 0; i < count; i++) {
			String task = format(TEMPLATE, poolId, workerId);
			queue.put(new Message(i, task));
			System.out.println(format(MSG, task, i));
		}
	}
}