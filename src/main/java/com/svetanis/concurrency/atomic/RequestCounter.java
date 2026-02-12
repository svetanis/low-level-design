package com.svetanis.concurrency.atomic;

import java.util.concurrent.atomic.AtomicInteger;

// Atomic increment

public final class RequestCounter {

	private final AtomicInteger requestCount = new AtomicInteger(0);

	public void onRequest() {
		requestCount.incrementAndGet();
	}

	public int getRequestCount() {
		return requestCount.get();
	}
}
