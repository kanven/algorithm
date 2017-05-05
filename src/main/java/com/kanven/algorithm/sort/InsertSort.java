package com.kanven.algorithm.sort;

public class InsertSort {

	private void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public void sort(int[] a) {
		for (int i = 1, len = a.length; i < len; i++) {
			for (int j = i; j > 0; j--) {
				if (a[j] >= a[j - 1]) {
					break;
				}
				swap(a, j, j - 1);
			}
		}
	}

	public static void main(String[] args) {
		int[] a = { 3, 7, 8, 5, 2, 1, 9, 5, 4 };
		InsertSort sort = new InsertSort();
		sort.sort(a);
	}

}
