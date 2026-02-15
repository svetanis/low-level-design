package com.svetanis.ood.parkinglot;

public interface ParkingLotService {

	public Ticket enter(Vehicle vehicle);

	public long exit(String ticketId);
}