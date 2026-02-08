package com.svetanis.ood.ratelimiter;

import static com.google.common.base.Optional.absent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.base.Optional;

public final class TokenBucketLimiterThreadSafe implements Limiter {

	private final static double MAX = 1000.0;

	private final int capacity;
	private final int rate;
	private final ConcurrentMap<String, TokenBucket> buckets;

	public TokenBucketLimiterThreadSafe(int capacity, int rate) {
		this.capacity = capacity;
		this.rate = rate;
		this.buckets = new ConcurrentHashMap<>();
	}

	@Override
	public RateLimitResult allow(String key) {
		TokenBucket bucket = buckets.computeIfAbsent(key, 
				k -> new TokenBucket(capacity, System.currentTimeMillis()));
		boolean allowed = false;
		int remaining = 0;
		Optional<Long> retryAfter = absent();
		synchronized (bucket) {
			long now = System.currentTimeMillis();
			long elapsed = now - bucket.lastRefillTime;
			double tokensToAdd = (elapsed * rate) / MAX;
			bucket.tokens = Math.min(capacity, bucket.tokens + tokensToAdd);
			bucket.lastRefillTime = now;
			if (bucket.tokens >= 1) {
				bucket.tokens -= 1;
				allowed = true;
				remaining = (int) Math.floor(bucket.tokens);
			} else {
				double tokensNeeded = 1 - bucket.tokens;
				retryAfter = Optional.of((long) Math.ceil((tokensNeeded * MAX) / rate));
			}
		}
		return new RateLimitResult(allowed, remaining, retryAfter);
	}

	public static final class TokenBucket {

		private double tokens;
		private long lastRefillTime;

		public TokenBucket(double initialTokens, long time) {
			this.tokens = initialTokens;
			this.lastRefillTime = time;
		}
	}
}
