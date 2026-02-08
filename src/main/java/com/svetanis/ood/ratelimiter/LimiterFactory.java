package com.svetanis.ood.ratelimiter;

import java.util.Collections;
import java.util.Map;

public final class LimiterFactory {

	@SuppressWarnings("unchecked")
	public Limiter create(Map<String, Object> map) {
		String algo = (String) map.get("algorithm");
		Map<String, Object> config = (Map<String, Object>) map.get("algoConfig");
		if (config == null) {
			config = Collections.emptyMap();
		}
		if ("TokenBucket".equals(algo)) {
			int rate = ((Number) config.getOrDefault("rate", 0)).intValue();
			int capacity = ((Number) config.getOrDefault("capacity", 0)).intValue();
			return new TokenBucketLimiter(capacity, rate);
		}
		if ("SlidingWindowLog".equals(algo)) {
			long window = ((Number) config.getOrDefault("window", 0)).longValue();
			int maxRequests = ((Number) config.getOrDefault("maxRequests", 0)).intValue();
			return new SlidingWindowLogLimiter(maxRequests, window);
		}
		throw new IllegalArgumentException("Unknown algorithm: " + algo);
	}
}
