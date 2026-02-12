package com.svetanis.concurrency.lock;

import java.util.HashMap;
import java.util.Map;

// Coarse Grained Locking

public final class TicketBookingCoarseGrained {

	private final Object bookingLock = new Object();
	private Map<String, String> seats = new HashMap<>();

	public boolean bookSeat(String sid, String vid) {
		synchronized (bookingLock) {
			if (seats.containsKey(sid)) {
				return false;
			}
			seats.put(sid, vid);
			return true;
		}
	}
}
