package com.svetanis.concurrency.loadleveling;

public final class Message {

	private final int id;
	private final String task;

	public Message(int id, String task) {
		this.id = id;
		this.task = task;
	}

	public int getId() {
		return id;
	}

	public String getTask() {
		return task;
	}

}