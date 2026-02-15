package com.svetanis.ood.parkinglot;

public final class Car implements Vehicle {

	@Override
	public SpotSize requiredSpotSize() {
		return SpotSize.REGULAR;
	}

	@Override
	public String toString() {
		return "Car";
	}

}
