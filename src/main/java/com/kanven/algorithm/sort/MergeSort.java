package com.kanven.algorithm.sort;

public class MergeSort {

	public void sort(int[] a) {
		// right = a.length - 1:坐标值均赋予有效坐标，可以避免不必要的推导
		sort(a, 0, a.length - 1, new int[a.length]);
	}

	private void merge(int[] a, int left, int center, int right, int[] temp) {
		int l = left, i = left;
		int le = center - 1; // 按照实际逻辑处理
		int r = center;
		while (l <= le && r <= right) {
			if (a[l] <= a[r]) {
				temp[i++] = a[l++];
			} else {
				temp[i++] = a[r++];
			}
		}
		while (l <= le) {
			temp[i++] = a[l++];
		}
		while (r <= right) {
			temp[i++] = a[r++];
		}
		int total = right - left + 1;
		for (int n = 0; n < total; n++) {
			a[left + n] = temp[left + n];
		}
	}

	private void sort(int[] a, int left, int right, int[] temp) {
		if (left < right) {
			int center = (right + left) / 2;
			sort(a, left, center, temp);
			sort(a, center + 1, right, temp);
			merge(a, left, center + 1, right, temp);
		}
	}

	public static void main(String[] args) {
		int[] a = { 3, 7, 8, 5, 2, 1, 9, 5, 4 };
		MergeSort merge = new MergeSort();
		merge.sort(a);
	}

}
