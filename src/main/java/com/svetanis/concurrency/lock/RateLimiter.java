package com.svetanis.concurrency.lock;

import java.util.HashMap;
import java.util.Map;

// Check and update atomically, under one lock. As a demonstration of that, this
// is correct: the read of the count and the write that follows it cannot be
// interleaved, which is the bug the lock exists to remove.
//
// It is NOT a rate limiter, and the name is the trap. The count per user only
// ever rises and is never reset, and there is no clock anywhere in the class --
// so this is a LIFETIME QUOTA of 100 requests per user. Measured: 100 allowed,
// then refused for good, still refused after sleeping.
//
// Real rate limiting needs permits that come back over time. The corpus already
// has it: see ood/ratelimiter/TokenBucketLimiter and its thread-safe sibling,
// where permits replenish at a fixed rate, and SlidingWindowLogLimiter for the
// other standard answer.
public final class RateLimiter {

	private final int maxRequests = 100;
	private final Object rateLimitLock = new Object();
	private Map<String, Integer> requestCounts = new HashMap<>();

	public boolean allowRequest(String uid) {
		synchronized (rateLimitLock) {
			int count = requestCounts.getOrDefault(uid, 0);
			if (count < maxRequests) {
				requestCounts.put(uid, count + 1);
				return true;
			}
			return false;
		}
	}
}
