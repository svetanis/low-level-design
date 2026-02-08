package com.svetanis.ood.ratelimiter;

import static com.google.common.base.Optional.absent;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.base.Optional;

public final class SlidingWindowLogLimiterThreadSafe implements Limiter {

	private final long window;
	private final int maxRequests;
	private final ConcurrentMap<String, Deque<Long>> logs;

	public SlidingWindowLogLimiterThreadSafe(int maxRequests, long window) {
		this.maxRequests = maxRequests;
		this.window = window;
		this.logs = new ConcurrentHashMap<>();
	}

	@Override
	public RateLimitResult allow(String key) {
		Deque<Long> log = logs.computeIfAbsent(key, k -> new ArrayDeque<>());
		boolean allowed = false;
		int remaining = 0;
		Optional<Long> retryAfter = absent();
		synchronized (log) {
			long now = System.currentTimeMillis();
			long cutoff = now - window;
			while (!log.isEmpty() && log.peekFirst() < cutoff) {
				log.pollFirst();
			}
			if (log.size() < maxRequests) {
				log.addLast(now);
				allowed = true;
				remaining = maxRequests - log.size();
			} else {
				long timestamp = log.peekFirst();
				retryAfter = Optional.of((timestamp + window) - now);
			}
		}
		return new RateLimitResult(allowed, remaining, retryAfter);
	}
}
