package com.svetanis.ood.ratelimiter;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public final class SlidingWindowLogLimiter implements Limiter {

	private final long window;
	private final int maxRequests;
	private final Map<String, Deque<Long>> logs;

	public SlidingWindowLogLimiter(int maxRequests, long window) {
		this.maxRequests = maxRequests;
		this.window = window;
		this.logs = new HashMap<>();
	}

	@Override
	public RateLimitResult allow(String key) {
		Deque<Long> log = logs.computeIfAbsent(key, k -> new ArrayDeque<>());
		long now = System.currentTimeMillis();
		long cutoff = now - window;
		while (!log.isEmpty() && log.peekFirst() < cutoff) {
			log.pollFirst();
		}
		if (log.size() < maxRequests) {
			log.addLast(now);
			int remaining = maxRequests - log.size();
			return new RateLimitResult(true, remaining, absent());
		}
		long timestamp = log.peekFirst();
		long retryAfter = (timestamp + window) - now;
		return new RateLimitResult(false, 0, of(retryAfter));
	}
}
