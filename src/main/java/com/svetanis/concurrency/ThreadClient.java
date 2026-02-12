package com.svetanis.concurrency;

public final class ThreadClient {

	public static void callRunnable() {
		RunnableThreadExample instance = new RunnableThreadExample();
		Thread thread = new Thread(instance);
		thread.start();
	}

	public static void callThread() {
		ThreadExample instance = new ThreadExample();
		instance.start();
	}

	public static void main(String[] args) {
		callRunnable();
		callThread();
	}

}