package com.svetanis.ood.parkinglot;

public final class ParkingSpot {

	private final String id;
	private final VehicleType type;

	public ParkingSpot(String id, VehicleType type) {
		this.id = id;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public VehicleType getType() {
		return type;
	}

}