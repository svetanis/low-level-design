package com.svetanis.ood.parkinglot;

import java.util.List;

import com.google.common.collect.ImmutableList;

public final class ParkingLot {

	private final long hourlyRateCents;
	private final ImmutableList<ParkingSpot> spots;

	public ParkingLot(long hourlyRateCents, List<ParkingSpot> spots) {
		this.spots = ImmutableList.copyOf(spots); // COPY: a caller's later change cannot reach the lot
		this.hourlyRateCents = hourlyRateCents;
	}

	public long getHourlyRateCents() {
		return hourlyRateCents;
	}

	public ImmutableList<ParkingSpot> getSpots() {
		return spots;
	}

}