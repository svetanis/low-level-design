package com.svetanis.concurrency.atomic;

import java.util.concurrent.atomic.AtomicInteger;

// Atomic CAS

public final class ConcurrencyTracker {

	private final AtomicInteger max = new AtomicInteger(0);

	public void updateMax(int curr) {
		while (true) {
			int observed = max.get();
			if (curr <= observed) {
				return;
			}
			if (max.compareAndSet(observed, curr)) {
				return;
			}
		}
	}

}
