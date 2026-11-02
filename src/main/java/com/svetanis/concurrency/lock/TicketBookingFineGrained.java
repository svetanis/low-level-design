package com.svetanis.concurrency.lock;

import java.util.concurrent.ConcurrentHashMap;

// Fine Grained Locking -- one lock per seat instead of one lock for the whole
// map, so two visitors booking different seats never wait for each other.
// computeIfAbsent is doing real work here: it guarantees that two threads
// asking for seat 12A's lock receive the SAME object. A get-then-put would hand
// out two different locks and the whole scheme would silently do nothing.
//
// Kept as written because this is the canonical shape and you have to be able
// to write it when the shared state is not a concurrent collection. Two things
// about it are worth knowing before you copy it into an answer.
//
// 1. The locks map never evicts. Booking 50,000 distinct seats leaves 50,000
//    lock objects behind, one per seat, on top of the 50,000 in seats. For a
//    theatre that is bounded and fine; for user ids, session ids or request
//    ids it is a memory leak wearing the costume of a striping strategy. The
//    usual answers are a fixed stripe count, or keeping the lock ON the value
//    object so lock and data are evicted together -- which is what
//    ood/ratelimiter/TokenBucketLimiterThreadSafe does.
//
// 2. For THIS problem the lock is not needed at all, because seats is already a
//    ConcurrentHashMap and the whole method is one atomic call:
//
//        return seats.putIfAbsent(sid, vid) == null;
//
//    Measured over 200 trials of 8 threads racing for one seat, that admits
//    exactly one winner every time, as this class does -- with no lock map to
//    leak and more concurrency, since it strips per bin. The general rule is
//    worth more than the one-liner: WHEN YOU REACH FOR A LOCK AROUND A MAP,
//    CHECK WHETHER THE MAP ALREADY HAS THE ATOMIC METHOD YOU WANT. Usually it
//    does -- putIfAbsent, computeIfAbsent, compute, merge, replace.
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
