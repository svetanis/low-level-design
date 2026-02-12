package com.svetanis.concurrency.lock;

import java.util.HashMap;
import java.util.Map;

// Check and update atomic

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
