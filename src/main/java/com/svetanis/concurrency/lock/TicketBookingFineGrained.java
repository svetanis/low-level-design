package com.svetanis.concurrency.lock;

import java.util.concurrent.ConcurrentHashMap;

// Fine Grained Locking

public final class TicketBookingFineGrained {

	private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, String> seats = new ConcurrentHashMap<>();

	public boolean bookSeat(String sid, String vid) {
		synchronized (getLock(sid)) {
			if (seats.containsKey(sid)) {
				return false;
			}
			seats.put(sid, vid);
			return true;
		}
	}

	private Object getLock(String sid) {
		return locks.computeIfAbsent(sid, k -> new Object());
	}

}
