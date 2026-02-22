package com.svetanis.ood.cache.lfu;

public final class DoublyLinkedList {

	private Node head;
	private Node tail;

	public DoublyLinkedList() {
		this.head = new Node(-1, -1);
		this.tail = new Node(-1, -1);
		head.next = tail;
		tail.prev = head;
	}

	public boolean isEmpty() {
		return head.next == tail;
	}

	public void addFirst(Node node) {
		node.prev = head;
		node.next = head.next;
		head.next.prev = node;
		head.next = node;
	}

	public Node removeLast() {
		if (isEmpty()) {
			return null;
		}
		return remove(tail.prev);
	}

	public Node remove(Node node) {
		node.prev.next = node.next;
		node.next.prev = node.prev;
		node.next = null;
		node.prev = null;
		return node;
	}
}
