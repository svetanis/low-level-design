package com.svetanis.ood.cache.lru;

public final class Node {

  public int key;
  public int value;
  public Node next;
  public Node prev;

  public Node(int key, int value) {
    this(key, value, null, null);
  }

  public Node(int key, int value, Node next, Node prev) {
    this.key = key;
    this.value = value;
    this.next = next;
    this.prev = prev;
  }

  @Override
  public String toString() {
    return key + ":" + value;
  }
}
