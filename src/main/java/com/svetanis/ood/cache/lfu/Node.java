package com.svetanis.ood.cache.lfu;

public final class Node {

	public int key;
	public int value;
	public int freq;
	public Node next;
	public Node prev;

	public Node(int key, int value) {
		this(key, value, 1, null, null);
	}

	public Node(int key, int value, int freq, Node next, Node prev) {
		this.key = key;
		this.value = value;
		this.freq = freq;
		this.next = next;
		this.prev = prev;
	}

	@Override
	public String toString() {
		return key + ":" + value;
	}
}
