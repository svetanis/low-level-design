package com.svetanis.ood.ratelimiter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RateLimiter {

	private final Limiter defaultLimiter;
	private final Map<String, Limiter> limiters;

	public RateLimiter(List<Map<String, Object>> configs, 
			Map<String, Object> defaultConfig) {
		this.limiters = new HashMap<>();
		LimiterFactory factory = factory(configs);
		this.defaultLimiter = factory.create(defaultConfig);
	}

	public RateLimitResult allow(String clientId, String endpoint) {
		Limiter limiter = limiters.getOrDefault(endpoint, defaultLimiter);
		return limiter.allow(clientId);
	}

	private LimiterFactory factory(List<Map<String, Object>> configs) {
		LimiterFactory factory = new LimiterFactory();
		for (Map<String, Object> config : configs) {
			String endpoint = (String) config.get("endpoint");
			if (endpoint == null) {
				continue;
			}
			Limiter limiter = factory.create(config);
			limiters.put(endpoint, limiter);
		}
		return factory;
	}
}
