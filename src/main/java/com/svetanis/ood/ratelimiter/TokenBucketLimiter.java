package com.svetanis.ood.ratelimiter;

import java.util.HashMap;
import java.util.Map;

public final class TokenBucketLimiter implements Limiter {

	private final static double MAX = 1000.0;

	private final int capacity;
	private final int rate;
	private final Map<String, TokenBucket> buckets;

	public TokenBucketLimiter(int capacity, int rate) {
		this.capacity = capacity;
		this.rate = rate;
		this.buckets = new HashMap<>();
	}

	@Override
	public RateLimitResult allow(String key) {
		TokenBucket bucket = buckets.computeIfAbsent(key, 
				k -> new TokenBucket(capacity, System.currentTimeMillis()));
		long now = System.currentTimeMillis();
		long elapsed = now - bucket.lastRefillTime;
		double tokensToAdd = (elapsed * rate) / MAX;
		bucket.tokens = Math.min(capacity, bucket.tokens + tokensToAdd);
		bucket.lastRefillTime = now;
		if (bucket.tokens >= 1) {
			bucket.tokens -= 1;
			int remaining = (int) Math.floor(bucket.tokens);
			return new RateLimitResult(true, remaining);
		}
		double tokensNeeded = 1 - bucket.tokens;
		long retryAfter = (long) Math.ceil((tokensNeeded * MAX) / rate);
		return new RateLimitResult(false, 0, retryAfter);
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
