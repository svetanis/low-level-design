package com.svetanis.api.health;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;
import static com.svetanis.java.base.Exceptions.illegalArgument;
import static com.svetanis.java.base.Exceptions.illegalState;
import static com.svetanis.java.base.collect.Lists.filter;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.transform;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.util.List;

import javax.inject.Provider;

import org.springframework.http.HttpStatus;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteSource;
import com.svetanis.api.health.domain.ScoreCard;
import com.svetanis.api.health.domain.SimpleCard;
import com.svetanis.api.health.domain.SimpleCard.Builder;
import com.svetanis.java.base.http.Http;
import com.svetanis.java.base.http.HttpResponse;
import com.svetanis.java.base.http.HttpService;
import com.svetanis.java.base.serialize.json.JsonSerializer;

public final class CardsProvider implements Provider<ImmutableList<SimpleCard>> {

	// TODO: make URL configurable in application properties
	private static final String URL = "https://www.healthit.gov/data/open-api?source=Meaningful-Use-Acceleration-Scorecard.csv";

	public CardsProvider(HttpService http, JsonSerializer json) {
		this.http = checkNotNull(http, "http");
		this.json = checkNotNull(json, "json");
	}

	private final HttpService http;
	private final JsonSerializer json;

	@Override
	public ImmutableList<SimpleCard> get() {
		List<ScoreCard> cards = cards();
		List<ScoreCard> filtered = filter(cards, scp());
		return transform(filtered, c -> build(c));
	}

	private SimpleCard build(ScoreCard sc) {
		double pct = parseDouble(sc.getPct_hospitals_mu().get());
		int percent = Double.valueOf(pct * 100).intValue();
		SimpleCard.Builder builder = new Builder();
		builder.withYear(parseInt(sc.getPeriod().get()));
		builder.withCode(sc.getRegion_code().get());
		builder.withState(sc.getRegion().get());
		builder.withPercent(percent);
		return builder.build();
	}

	private Predicate<ScoreCard> scp() {
		Predicate<ScoreCard> rp = c -> c.getRegion().isPresent();
		Predicate<ScoreCard> cp = c -> c.getRegion_code().isPresent();
		Predicate<ScoreCard> rcp = and(cp, not(c -> c.getRegion_code().get().equals("US")));
		Predicate<ScoreCard> pp = c -> c.getPeriod().isPresent();
		Predicate<ScoreCard> hp = c -> c.getPct_hospitals_mu().isPresent();
		return and(rp, rcp, pp, hp);
	}

	private ImmutableList<ScoreCard> cards() {
		try {
			HttpResponse response = http.get(URL);
			if (Http.ok(response)) {
				ByteSource stdout = response.getStdout().get();
				ScoreCard[] cards = json.read(stdout, ScoreCard[].class);
				return newList(cards);
			} else {
				HttpStatus status = HttpStatus.valueOf(response.getStatus());
				throw illegalArgument("%s --> %s", status, response.getMessage().or("unknown error"));
			}
		} catch (IOException e) {
			throw illegalState(e);
		}
	}

}
