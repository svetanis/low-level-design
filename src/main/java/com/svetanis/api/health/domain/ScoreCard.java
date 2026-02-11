package com.svetanis.api.health.domain;

import static com.svetanis.java.base.Optionals.fromTrimToNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Optional;

@JsonDeserialize(builder = ScoreCard.Builder.class)
public final class ScoreCard {
	private final Optional<String> region;
	private final Optional<String> region_code;
	private final Optional<String> period;
	private final Optional<String> pct_md_pa_np_mu_aiu;
	private final Optional<String> pct_md_pa_np_mu;
	private final Optional<String> pct_md_mu_aiu;
	private final Optional<String> pct_md_mu;
	private final Optional<String> pct_np_mu_aiu;
	private final Optional<String> pct_np_mu;
	private final Optional<String> pct_pa_mu_aiu;
	private final Optional<String> pct_pa_mu;
	private final Optional<String> pct_hospitals_mu_aiu;
	private final Optional<String> pct_hospitals_mu;
	private final Optional<String> pct_cah_small_rural_mu_aiu;
	private final Optional<String> pct_cah_small_rural_mu;

	private ScoreCard(Builder builder) {
		this.region = fromTrimToNull(builder.region);
		this.region_code = fromTrimToNull(builder.region_code);
		this.period = fromTrimToNull(builder.period);
		this.pct_cah_small_rural_mu = fromTrimToNull(builder.pct_cah_small_rural_mu);
		this.pct_cah_small_rural_mu_aiu = fromTrimToNull(builder.pct_cah_small_rural_mu_aiu);
		this.pct_hospitals_mu = fromTrimToNull(builder.pct_hospitals_mu);
		this.pct_hospitals_mu_aiu = fromTrimToNull(builder.pct_hospitals_mu_aiu);
		this.pct_md_mu = fromTrimToNull(builder.pct_md_mu);
		this.pct_md_mu_aiu = fromTrimToNull(builder.pct_md_mu_aiu);
		this.pct_md_pa_np_mu = fromTrimToNull(builder.pct_md_pa_np_mu);
		this.pct_md_pa_np_mu_aiu = fromTrimToNull(builder.pct_md_pa_np_mu_aiu);
		this.pct_np_mu = fromTrimToNull(builder.pct_np_mu);
		this.pct_np_mu_aiu = fromTrimToNull(builder.pct_np_mu_aiu);
		this.pct_pa_mu = fromTrimToNull(builder.pct_pa_mu);
		this.pct_pa_mu_aiu = fromTrimToNull(builder.pct_pa_mu_aiu);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String region;
		private String region_code;
		private String period;
		private String pct_md_pa_np_mu_aiu;
		private String pct_md_pa_np_mu;
		private String pct_md_mu_aiu;
		private String pct_md_mu;
		private String pct_np_mu_aiu;
		private String pct_np_mu;
		private String pct_pa_mu_aiu;
		private String pct_pa_mu;
		private String pct_hospitals_mu_aiu;
		private String pct_hospitals_mu;
		private String pct_cah_small_rural_mu_aiu;
		private String pct_cah_small_rural_mu;

		public ScoreCard build() {
			return new ScoreCard(this);
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getRegion_code() {
			return region_code;
		}

		public void setRegion_code(String region_code) {
			this.region_code = region_code;
		}

		public String getPeriod() {
			return period;
		}

		public void setPeriod(String period) {
			this.period = period;
		}

		public String getPct_md_pa_np_mu_aiu() {
			return pct_md_pa_np_mu_aiu;
		}

		public void setPct_md_pa_np_mu_aiu(String pct_md_pa_np_mu_aiu) {
			this.pct_md_pa_np_mu_aiu = pct_md_pa_np_mu_aiu;
		}

		public String getPct_md_pa_np_mu() {
			return pct_md_pa_np_mu;
		}

		public void setPct_md_pa_np_mu(String pct_md_pa_np_mu) {
			this.pct_md_pa_np_mu = pct_md_pa_np_mu;
		}

		public String getPct_md_mu_aiu() {
			return pct_md_mu_aiu;
		}

		public void setPct_md_mu_aiu(String pct_md_mu_aiu) {
			this.pct_md_mu_aiu = pct_md_mu_aiu;
		}

		public String getPct_md_mu() {
			return pct_md_mu;
		}

		public void setPct_md_mu(String pct_md_mu) {
			this.pct_md_mu = pct_md_mu;
		}

		public String getPct_np_mu_aiu() {
			return pct_np_mu_aiu;
		}

		public void setPct_np_mu_aiu(String pct_np_mu_aiu) {
			this.pct_np_mu_aiu = pct_np_mu_aiu;
		}

		public String getPct_np_mu() {
			return pct_np_mu;
		}

		public void setPct_np_mu(String pct_np_mu) {
			this.pct_np_mu = pct_np_mu;
		}

		public String getPct_pa_mu_aiu() {
			return pct_pa_mu_aiu;
		}

		public void setPct_pa_mu_aiu(String pct_pa_mu_aiu) {
			this.pct_pa_mu_aiu = pct_pa_mu_aiu;
		}

		public String getPct_pa_mu() {
			return pct_pa_mu;
		}

		public void setPct_pa_mu(String pct_pa_mu) {
			this.pct_pa_mu = pct_pa_mu;
		}

		public String getPct_hospitals_mu_aiu() {
			return pct_hospitals_mu_aiu;
		}

		public void setPct_hospitals_mu_aiu(String pct_hospitals_mu_aiu) {
			this.pct_hospitals_mu_aiu = pct_hospitals_mu_aiu;
		}

		public String getPct_hospitals_mu() {
			return pct_hospitals_mu;
		}

		public void setPct_hospitals_mu(String pct_hospitals_mu) {
			this.pct_hospitals_mu = pct_hospitals_mu;
		}

		public String getPct_cah_small_rural_mu_aiu() {
			return pct_cah_small_rural_mu_aiu;
		}

		public void setPct_cah_small_rural_mu_aiu(String pct_cah_small_rural_mu_aiu) {
			this.pct_cah_small_rural_mu_aiu = pct_cah_small_rural_mu_aiu;
		}

		public String getPct_cah_small_rural_mu() {
			return pct_cah_small_rural_mu;
		}

		public void setPct_cah_small_rural_mu(String pct_cah_small_rural_mu) {
			this.pct_cah_small_rural_mu = pct_cah_small_rural_mu;
		}

	}

	public Optional<String> getRegion() {
		return region;
	}

	public Optional<String> getRegion_code() {
		return region_code;
	}

	public Optional<String> getPeriod() {
		return period;
	}

	public Optional<String> getPct_md_pa_np_mu_aiu() {
		return pct_md_pa_np_mu_aiu;
	}

	public Optional<String> getPct_md_pa_np_mu() {
		return pct_md_pa_np_mu;
	}

	public Optional<String> getPct_md_mu_aiu() {
		return pct_md_mu_aiu;
	}

	public Optional<String> getPct_md_mu() {
		return pct_md_mu;
	}

	public Optional<String> getPct_np_mu_aiu() {
		return pct_np_mu_aiu;
	}

	public Optional<String> getPct_np_mu() {
		return pct_np_mu;
	}

	public Optional<String> getPct_pa_mu_aiu() {
		return pct_pa_mu_aiu;
	}

	public Optional<String> getPct_pa_mu() {
		return pct_pa_mu;
	}

	public Optional<String> getPct_hospitals_mu_aiu() {
		return pct_hospitals_mu_aiu;
	}

	public Optional<String> getPct_hospitals_mu() {
		return pct_hospitals_mu;
	}

	public Optional<String> getPct_cah_small_rural_mu_aiu() {
		return pct_cah_small_rural_mu_aiu;
	}

	public Optional<String> getPct_cah_small_rural_mu() {
		return pct_cah_small_rural_mu;
	}

}
