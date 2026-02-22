package com.svetanis.ood.cache.lru;

import java.util.HashMap;
import java.util.Map;

// 146. LRU Cache

// design and implement a data structure
// for Least Recently Used (LRU) cache.
// it should support get and put operations

// get(key): get the value (which will always
// be positive) of the key if the key exists
// in the cache, otherwise return -1

// put(key, value): set or insert the value
// if the key is not already present. when 
// the cache reached its capacity, it should
// invalidate the least recently used item
// before inserting a new item.

// the cache is initialized with a positive capacity
// both get and put operations are in O(1) time complexity

public final class LruCache {

	private int size;
	private Node head;
	private Node tail;
	private Map<Integer, Node> map;

	public LruCache(int size) {
		this.size = size;
		this.head = null;
		this.tail = null;
		this.map = new HashMap<>();
	}

	public int get(int key) {
		Node node = map.get(key);
		if (node == null) {
			return -1;
		}
		moveToFront(node);
		return node.value;
	}

	public void put(int key, int value) {
		Node node = map.get(key);
		if (node != null) {
			node.value = value;
			moveToFront(node);
		} else {
			node = new Node(key, value);
			addFirst(node);
			map.put(key, node);
			removeLast();
		}
	}

	private void moveToFront(Node node) {
		if (node == head || node == null) {
			return;
		}
		remove(node);
		addFirst(node);
	}

	public void addFirst(Node node) {
		if (node == null) {
			return;
		}
		node.next = head;
		node.prev = null;

		if (head != null) {
			head.prev = node;
		}
		head = node;
		if (tail == null) {
			tail = node;
		}
	}

	private void removeLast() {
		if (map.size() > size) {
			map.remove(tail.key);
			remove(tail);
		}
	}

	private void remove(Node node) {
		if (node == null) {
			return;
		}

		if (node == head) {
			head = node.next;
		}

		if (node == tail) {
			tail = node.prev;
		}

		if (node.prev != null) {
			node.prev.next = node.next;
		}

		if (node.next != null) {
			node.next.prev = node.prev;
		}
		node.next = null;
		node.prev = null;
	}
}
