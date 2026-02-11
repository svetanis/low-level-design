package com.svetanis.api.health;

import com.google.common.collect.ImmutableList;
import com.svetanis.api.health.domain.SimpleCard;

public interface IncentiveMeasureService {

	ImmutableList<SimpleCard> incentives(int year);

}
