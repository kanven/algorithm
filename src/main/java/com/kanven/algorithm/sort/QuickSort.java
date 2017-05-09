package com.kanven.algorithm.sort;

public class QuickSort {

	private void swap(int[] a, int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	private int partition(int[] a, int left, int right, int pivot) {
		int pv = a[pivot];
		swap(a, pivot, right);
		int store = left;
		for (int i = left; i < right; i++) {
			int v = a[i];
			if (v < pv) {
				swap(a, i, store);
				store++;
			}
		}
		swap(a, store, right);
		return store;
	}

	private void sort(int[] a, int left, int right) {
		if (left < right) {
			int pivot = ((right - left) / 2) + left;
			int index = partition(a, left, right, pivot);
			sort(a, left, index - 1);
			sort(a, index + 1, right);
		}
	}

	public void sort(int[] a) {
		sort(a, 0, a.length - 1);
	}

	public static void main(String[] args) {
		int[] a = { 3, 7, 8, 5, 2, 1, 9, 5, 4 };
		QuickSort quick = new QuickSort();
		quick.sort(a);
	}

}
