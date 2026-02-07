package com.svetanis.ood.parkinglot;

public interface ParkingLotService {

	public Ticket enter(VehicleType vehicleType);

	public long exit(String ticketId);
}