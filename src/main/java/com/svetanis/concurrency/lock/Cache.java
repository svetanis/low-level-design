package com.svetanis.concurrency.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// Read Write Lock Cache

public final class Cache {

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Map<String, String> map = new HashMap<>();

	public String get(String key) {
		lock.readLock().lock();
		try {
			return map.get(key);
		} finally {
			lock.readLock().unlock();
		}
	}

	public void put(String key, String val) {
		lock.writeLock().lock();
		try {
			map.put(key, val);
		} finally {
			lock.writeLock().unlock();
		}
	}

}
