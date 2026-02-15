package com.svetanis.ood.parkinglot;

public final class Motorcycle implements Vehicle {

	@Override
	public SpotSize requiredSpotSize() {
		return SpotSize.MOTORCYCLE;
	}

	@Override
	public String toString() {
		return "Motorcycle";
	}
}
