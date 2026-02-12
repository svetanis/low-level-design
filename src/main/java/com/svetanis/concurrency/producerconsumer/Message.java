package com.svetanis.concurrency.producerconsumer;

public final class Message {

	private final int id;
	private final String producer;

	public Message(int id, String producer) {
		this.id = id;
		this.producer = producer;
	}

	public int getId() {
		return id;
	}

	public String getProducer() {
		return producer;
	}

}