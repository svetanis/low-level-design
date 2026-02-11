package com.svetanis.api.health.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Ordering.natural;
import static com.svetanis.java.base.Objects.equalByComparison;
import static com.svetanis.java.base.validate.Validation.doValidation;
import static java.util.Objects.hash;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.svetanis.java.base.validate.IgnoreValidation;

@JsonDeserialize(builder = SimpleCard.Builder.class)
public final class SimpleCard implements Comparable<SimpleCard> {

	private final int year;
	private final int percent;
	private final String code;
	private final String state;
	@IgnoreValidation
	private final int hash;

	private SimpleCard(Builder builder) {
		this.year = builder.year;
		this.percent = builder.percent;
		this.code = builder.code;
		this.state = builder.state;
		this.hash = hash(code, state, year, percent);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private int year = -1;
		private int percent = -1;
		private String code;
		private String state;

		public Builder withPercent(int percent) {
			this.percent = percent;
			return this;
		}

		public Builder withYear(int year) {
			this.year = year;
			return this;
		}

		public Builder withCode(String code) {
			this.code = code;
			return this;
		}

		public Builder withState(String state) {
			this.state = state;
			return this;
		}

		public SimpleCard build() {
			return validate(new SimpleCard(this));
		}

		private static SimpleCard validate(SimpleCard instance) {
			doValidation(instance);
			checkArgument(instance.year >= 1000 && instance.year <= 9999, "year must be 4 digits");
			return instance;
		}
	}

	@Override
	public int compareTo(SimpleCard other) {
		ComparisonChain chain = ComparisonChain.start();
		chain = chain.compare(percent, other.percent, natural().reverse());
		chain = chain.compare(year, other.year);
		chain = chain.compare(code, other.code);
		chain = chain.compare(state, other.state);
		return chain.result();
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		return equalByComparison(this, object, true);
	}

	public String getCode() {
		return code;
	}

	public String getState() {
		return state;
	}

	public int getYear() {
		return year;
	}

	public int getPercent() {
		return percent;
	}

	@Override
	public String toString() {
		return Joiner.on("/").join(code, state, year, percent);
	}
}
