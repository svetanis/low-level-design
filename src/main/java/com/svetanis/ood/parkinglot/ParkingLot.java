package com.svetanis.ood.parkinglot;

import java.util.List;

public final class ParkingLot {

	private final long hourlyRateCents;
	private final List<ParkingSpot> spots;

	public ParkingLot(long hourlyRateCents, List<ParkingSpot> spots) {
		this.spots = spots;
		this.hourlyRateCents = hourlyRateCents;
	}

	public long getHourlyRateCents() {
		return hourlyRateCents;
	}

	public List<ParkingSpot> getSpots() {
		return spots;
	}

}