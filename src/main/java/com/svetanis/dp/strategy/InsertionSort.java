package com.svetanis.dp.strategy;

import java.util.List;

public final class InsertionSort implements Sorting {

	@Override
	public <C extends Comparable<C>> void sort(List<C> list) {
		// Time Complexity: O(n^2)

		for (int i = 0; i < list.size(); i++) {
			C temp = list.get(i);
			int j = i;
			while (j > 0 && list.get(j - 1).compareTo(temp) > 0) { // LARGER: compareTo promises a sign, not 1
				list.set(j, list.get(j - 1));
				j--;
			}
			list.set(j, temp);
		}
	}
}
