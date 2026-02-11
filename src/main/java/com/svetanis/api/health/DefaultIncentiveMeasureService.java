package com.svetanis.api.health;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.svetanis.java.base.collect.Lists.filter;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.api.health.domain.SimpleCard;
import com.svetanis.java.base.http.HttpService;
import com.svetanis.java.base.serialize.json.JsonSerializer;

public final class DefaultIncentiveMeasureService implements IncentiveMeasureService {

	public DefaultIncentiveMeasureService(HttpService http, JsonSerializer json) {
		this.http = checkNotNull(http);
		this.json = checkNotNull(json);
	}

	private final HttpService http;
	private final JsonSerializer json;

	@Override
	public ImmutableList<SimpleCard> incentives(int year) {
		List<SimpleCard> list = new CardsProvider(http, json).get();
		return filter(list, c -> c.getYear() == year);
	}

}
