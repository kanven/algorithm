package com.kanven.algorithm.sort;

public class HeapSort {

	private int[] dts;

	private int len;

	private int cursor = 0;

	private HeapSort(int len) {
		this.dts = new int[len];
		this.len = len;
	}

	public void add(int item) {
		if (cursor >= len) {
			return;
		}
		dts[cursor] = item;
		up(cursor);
		cursor++;
	}

	public int delete() {
		int out = dts[0];
		dts[0] = dts[cursor - 1];
		dts[cursor - 1] = -1;
		cursor--;
		down(0);
		return out;
	}

	private void swap(int i, int j) {
		int temp = dts[i];
		dts[i] = dts[j];
		dts[j] = temp;
	}

	private void up(int i) {
		if (i > 0) {
			int p = i % 2 == 0 ? (i / 2 - 1) : (i - 1) / 2;
			if (p >= 0 && dts[p] < dts[i]) {
				swap(p, i);
				up(p);
			}
		}
	}

	private void down(int i) {
		int left = 2 * i + 1;
		int right = 2 * i + 2;
		if (left >= cursor && right >= cursor) {
			return;
		}
		if (right >= cursor) {
			if (dts[left] > dts[i]) {
				swap(left, i);
			}
			return;
		}
		int index = dts[left] > dts[right] ? left : right;
		swap(index, i);
		down(index);
	}

	public static void main(String[] args) {
		int[] a = { 3, 7, 8, 5, 2, 1, 9, 5, 4 };
		HeapSort heap = new HeapSort(a.length);
		for (int i : a) {
			heap.add(i);
		}
		for (int i = 0; i < a.length; i++) {
			System.out.println(heap.delete() + ",");
		}
	}

}
