package com.svetanis.concurrency.atomic;

import java.util.concurrent.atomic.AtomicInteger;

// Atomic CAS -- the largest value any thread has reported, kept without a lock.
//
// compareAndSet(observed, curr) writes curr only if max still holds observed. If another thread
// changed max in between, the write fails and the loop reads max again. It ends as soon as curr is
// no bigger than what is stored (nothing to do) or the write succeeds. AtomicInteger's
// accumulateAndGet(curr, Math::max) runs the same loop inside the library.
//
// Time: O(1) per call when uncontended; a failed compareAndSet costs one more read and try.
// Space: O(1).

public final class ConcurrencyTracker {

	private static final String RESULT = "max = %d (want %d)%n";

	private final AtomicInteger max = new AtomicInteger(0);

	public void updateMax(int curr) {
		int observed = max.get();
		while (curr > observed && !max.compareAndSet(observed, curr)) { // RETRY: someone changed max
			observed = max.get(); // READ again, then compare again
		}
	}

	public int get() {
		return max.get();
	}

	public static void main(String[] args) throws InterruptedException {
		ConcurrencyTracker tracker = new ConcurrencyTracker();
		Thread[] threads = new Thread[4];
		for (int t = 0; t < threads.length; t++) {
			int offset = t;
			threads[t] = new Thread(() -> report(tracker, offset));
			threads[t].start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
		System.out.printf(RESULT, tracker.get(), 400_000 - 1); // max = 399999 (want 399999)
	}

	// thread t reports t, t + 4, t + 8, ... below 400,000, so together the threads report every value
	private static void report(ConcurrencyTracker tracker, int offset) {
		for (int value = offset; value < 400_000; value += 4) {
			tracker.updateMax(value);
		}
	}
}
