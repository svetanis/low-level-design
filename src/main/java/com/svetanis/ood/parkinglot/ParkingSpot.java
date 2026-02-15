package com.svetanis.ood.parkinglot;

public final class ParkingSpot {

	private final String id;
	private final SpotSize type;

	public ParkingSpot(String id, SpotSize type) {
		this.id = id;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public SpotSize getType() {
		return type;
	}

}