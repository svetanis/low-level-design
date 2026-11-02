package com.svetanis.dp.strategy;

import static com.svetanis.java.base.utils.Swap.swap;

import java.util.List;

public final class HeapSort implements Sorting {

	@Override
	public <C extends Comparable<C>> void sort(List<C> list) {
		// Time Complexity: O(n log n)

		int n = list.size();
		asMaxHeap(list);
		for (int i = n - 1; i >= 0; i--) {
			swap(list, 0, i);
			heapify(list, i, 0);
		}
	}

	private <C extends Comparable<C>> void asMaxHeap(List<C> list) {
		int n = list.size();
		for (int i = n / 2 - 1; i >= 0; i--) {
			heapify(list, n, i);
		}
	}

	private <C extends Comparable<C>> void heapify(List<C> list, int n, int i) {
		// Time Complexity: O(log n)

		int root = i;
		int left = left(i);
		int right = right(i);

		// if left child is greater than root
		if (left < n && list.get(left).compareTo(list.get(root)) > 0) { // LARGER: any positive, not just 1
			root = left;
		}

		// if right child is greater than root
		if (right < n && list.get(right).compareTo(list.get(root)) > 0) {
			root = right;
		}

		// change root, if needed
		if (root != i) {
			swap(list, root, i);
			heapify(list, n, root);
		}
	}

	private static int left(int i) {
		return 2 * i + 1;
	}

	private static int right(int i) {
		return 2 * i + 2;
	}

}