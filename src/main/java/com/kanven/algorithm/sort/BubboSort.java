package com.kanven.algorithm.sort;

public class BubboSort {

	private void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public void sort(int[] a) {
		int l = a.length;
		for (int j = l - 1; j >= 1; j--) {
			for (int i = 1; i <= j; i++) {
				if (a[i - 1] < a[i]) {
					swap(a, i - 1, i);
				}
			}
		}
	}

	public static void main(String[] args) {
		int[] a = { 3, 7, 8, 5, 2, 1, 9, 5, 4 };
		BubboSort bubbo = new BubboSort();
		bubbo.sort(a);
	}

}
