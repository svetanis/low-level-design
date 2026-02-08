package com.svetanis.ood.ratelimiter;

import com.google.common.base.Optional;

public final class RateLimitResult {

	private final boolean allowed;
	private final int remaining;
	private final Optional<Long> retryAfter;

	public RateLimitResult(boolean allowed, int remaining, 
			Optional<Long> retryAfter) {
		this.allowed = allowed;
		this.remaining = remaining;
		this.retryAfter = retryAfter;
	}

	public boolean isAllowed() {
		return allowed;
	}

	public int getRemaining() {
		return remaining;
	}

	public Optional<Long> getRetryAfter() {
		return retryAfter;
	}

}
