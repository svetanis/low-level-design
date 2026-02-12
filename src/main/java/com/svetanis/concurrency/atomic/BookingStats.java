package com.svetanis.concurrency.atomic;

import java.util.concurrent.atomic.AtomicInteger;

// Atomic Counter

public final class BookingStats {

	private final AtomicInteger bookedCount = new AtomicInteger(0);

	public void onSeatBooked() {
		bookedCount.incrementAndGet();
	}

	public int getBookedCount() {
		return bookedCount.get();
	}
}
