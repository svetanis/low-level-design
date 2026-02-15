package com.svetanis.ood.parkinglot;

public final class Ticket {

	private final long entryTime;
	private final String id;
	private final String spotId;
	private final Vehicle vehicle;

	public Ticket(long entryTime, String id, String spotId, Vehicle vehicle) {
		super();
		this.entryTime = entryTime;
		this.id = id;
		this.spotId = spotId;
		this.vehicle = vehicle;
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

	public Vehicle getVehicle() {
		return vehicle;
	}
}