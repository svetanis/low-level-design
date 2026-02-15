package com.svetanis.ood.ratelimiter;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;

import com.google.common.base.Optional;

public final class RateLimitResult {

	private final boolean allowed;
	private final int remaining;
	private final Optional<Long> retryAfter;

	public RateLimitResult(boolean allowed, int remaining) {
		this(allowed, remaining, absent());
	}

	public RateLimitResult(boolean allowed, int remaining, long retryAfter) {
		this(allowed, remaining, of(retryAfter));
	}

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
