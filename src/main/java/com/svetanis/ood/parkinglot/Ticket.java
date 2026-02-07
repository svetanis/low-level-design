package com.svetanis.ood.parkinglot;

public final class Ticket {

	private final long entryTime;
	private final String id;
	private final String spotId;
	private final VehicleType vehicleType;

	public Ticket(long entryTime, String id, String spotId, VehicleType vehicleType) {
		super();
		this.entryTime = entryTime;
		this.id = id;
		this.spotId = spotId;
		this.vehicleType = vehicleType;
	}

	public long getEntryTime() {
		return entryTime;
	}

	public String getId() {
		return id;
	}

	public String getSpotId() {
		return spotId;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

}