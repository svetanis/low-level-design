package com.svetanis.api.health.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.svetanis.api.health.DefaultIncentiveMeasureService;
import com.svetanis.api.health.IncentiveMeasureService;
import com.svetanis.java.base.http.DefaultHttpService;
import com.svetanis.java.base.http.HttpService;
import com.svetanis.java.base.serialize.json.DefaultJsonSerializer;
import com.svetanis.java.base.serialize.json.JsonSerializer;

@Configuration
public class AppConfig {

	@Bean
	protected IncentiveMeasureService incentiveMeasureService() {
		HttpService http = new DefaultHttpService();
		JsonSerializer json = new DefaultJsonSerializer();
		return new DefaultIncentiveMeasureService(http, json);
	}
}
