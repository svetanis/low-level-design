package com.svetanis.ood.parkinglot;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.not;
import static com.svetanis.java.base.collect.Iterables.firstMatch;
import static com.svetanis.java.base.collect.Maps.getIfPresent;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

public final class DefaultParkingLotService implements ParkingLotService {

	private final ParkingLot parkingLot;
	private final Set<String> occupiedSpotIds;
	private final Map<String, Ticket> activeTickets;

	public DefaultParkingLotService(long hourlyRateCents, List<ParkingSpot> spots) {
		this.parkingLot = new ParkingLot(hourlyRateCents, spots);
		this.activeTickets = new HashMap<>();
		this.occupiedSpotIds = new HashSet<>();
	}

	@Override
	public Ticket enter(Vehicle vehicle) {
		Optional<ParkingSpot> optional = availableSpot(vehicle);
		if (!optional.isPresent()) {
			String msg = "No available spots for vehicle type %s";
			throw new RuntimeException(String.format(msg, vehicle));
		}
		ParkingSpot spot = optional.get();
		occupiedSpotIds.add(spot.getId());
		String tid = UUID.randomUUID().toString();
		long entryTime = System.currentTimeMillis();
		Ticket ticket = new Ticket(entryTime, tid, spot.getId(), vehicle);
		activeTickets.put(tid, ticket);
		return ticket;
	}

	private Optional<ParkingSpot> availableSpot(Vehicle vehicle) {
		List<ParkingSpot> spots = parkingLot.getSpots();
		Predicate<ParkingSpot> stp = s -> s.getType() == vehicle.requiredSpotSize();
		Predicate<ParkingSpot> idp = s -> occupiedSpotIds.contains(s.getId());
		Predicate<ParkingSpot> psp = and(stp, not(idp));
		return firstMatch(spots, psp);
	}

	@Override
	public long exit(String ticketId) {
		if (isBlank(ticketId)) {
			throw new RuntimeException("Invalid ticket id");
		}
		Optional<Ticket> optional = getIfPresent(activeTickets, ticketId);
		if (!optional.isPresent()) {
			throw new RuntimeException("Ticket not found or already used");
		}
		Ticket ticket = optional.get();
		long exitType = System.currentTimeMillis();
		long fee = computeFee(ticket.getEntryTime(), exitType);
		activeTickets.remove(ticket.getId());
		occupiedSpotIds.remove(ticket.getSpotId());
		return fee;
	}

	private long computeFee(long entryTime, long exitTime) {
		long millis = 1000 * 60 * 60;
		long duration = exitTime - entryTime;
		long hours = duration / millis;
		if (duration % millis > 0) {
			hours += 1;
		}
		return hours * parkingLot.getHourlyRateCents();
	}
}
