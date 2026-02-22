package com.svetanis.ood.cache.lru;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Deque;
import java.util.Map;

// 146. LRU Cache

public final class LruSimple {

	private int maxSize;
	private Deque<Integer> queue;
	private Map<Integer, Integer> map;

	public LruSimple(int maxSize) {
		this.maxSize = maxSize;
		this.map = newHashMap();
		this.queue = newLinkedList();
	}

	private boolean isFull() {
		return queue.size() >= maxSize;
	}

	public int get(int key) {
		if (map.get(key) == null) {
			return -1;
		}
		queue.removeFirstOccurrence(key);
		queue.addFirst(key);
		return map.get(key);
	}

	public void put(int key, int value) {
		if (map.containsKey(key)) {
			queue.removeFirstOccurrence(key);
		}

		while (isFull()) {
			int last = queue.removeLast();
			map.remove(last);
		}

		queue.addFirst(key);
		map.put(key, value);
	}
}
