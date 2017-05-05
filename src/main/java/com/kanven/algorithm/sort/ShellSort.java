package com.kanven.algorithm.sort;

public class ShellSort {

	private void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public void sort(int[] a) {
		int len = a.length;
		for (int gap = len / 2; gap >= 1; gap = gap / 2) {
			for (int i = gap; i < len; i += gap) {
				for (int j = i - gap; j >= 0; j -= gap) {
					if (a[j] <= a[j + gap]) {
						break;
					}
					swap(a, j, j + gap);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] a = { 3, 7, 8, 5, 2, 1, 9, 5, 4 };
		ShellSort shell = new ShellSort();
		shell.sort(a);
	}

}
