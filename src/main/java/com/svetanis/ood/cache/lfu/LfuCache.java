package com.svetanis.ood.cache.lfu;

import java.util.HashMap;
import java.util.Map;

// 460. LFU Cache

public final class LfuCache {

	private int size;
	private int minFrequency;
	private Map<Integer, Node> cache;
	private Map<Integer, DoublyLinkedList> frequencies;

	public LfuCache(int size) {
		this.size = size;
		this.cache = new HashMap<>();
		this.frequencies = new HashMap<>();
	}

	public int get(int key) {
		if (size == 0) {
			return -1;
		}
		if (!cache.containsKey(key)) {
			return -1;
		}
		Node node = cache.get(key);
		increaseFrequency(node);
		return node.value;
	}

	public void put(int key, int value) {
		if (size == 0) {
			return;
		}
		if (cache.containsKey(key)) {
			Node node = cache.get(key);
			node.value = value;
			increaseFrequency(node);
			return;
		}
		if (cache.size() == size) {
			DoublyLinkedList dll = frequencies.get(minFrequency);
			Node remove = dll.removeLast();
			cache.remove(remove.key);
		}
		Node node = new Node(key, value);
		addNodeToFrequency(node);
		cache.put(key, node);
		minFrequency = 1;
	}

	private void increaseFrequency(Node node) {
		int freq = node.freq;
		DoublyLinkedList dll = frequencies.get(freq);
		dll.remove(node);
		if (dll.isEmpty()) {
			frequencies.remove(freq);
			if (freq == minFrequency) {
				minFrequency++;
			}
		}
		node.freq++;
		addNodeToFrequency(node);
	}

	private void addNodeToFrequency(Node node) {
		int freq = node.freq;
		DoublyLinkedList dll = frequencies.getOrDefault(freq, new DoublyLinkedList());
		dll.addFirst(node);
		frequencies.put(freq, dll);
	}
}
