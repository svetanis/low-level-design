package com.svetanis.api.health;

import java.util.List;

import org.junit.Test;

import com.svetanis.api.health.DefaultIncentiveMeasureService;
import com.svetanis.api.health.IncentiveMeasureService;
import com.svetanis.api.health.domain.SimpleCard;
import com.svetanis.java.base.http.DefaultHttpService;
import com.svetanis.java.base.http.HttpService;
import com.svetanis.java.base.serialize.json.DefaultJsonSerializer;
import com.svetanis.java.base.serialize.json.JsonSerializer;

public class IncentiveMeasureServiceTest {

	private final HttpService http = new DefaultHttpService();
	private final JsonSerializer json = new DefaultJsonSerializer();

	@Test
	public void test() {
		IncentiveMeasureService ims = new DefaultIncentiveMeasureService(http, json);
		List<SimpleCard> cards = ims.incentives(2014);
		System.out.println(cards.size());
		for (SimpleCard sc : cards) {
			System.out.println(sc);
		}
	}
}
