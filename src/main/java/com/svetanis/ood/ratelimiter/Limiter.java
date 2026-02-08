package com.svetanis.ood.ratelimiter;

public interface Limiter {

	RateLimitResult allow(String key);

}
