package com.svetanis.ood.parkinglot;

public final class Truck implements Vehicle {

	@Override
	public SpotSize requiredSpotSize() {
		return SpotSize.LARGE;
	}

	@Override
	public String toString() {
		return "Truck";
	}

}
