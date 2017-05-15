package com.kanven.algorithm.lbs;

import java.util.Arrays;

/**
 * 最大公共子串
 * 
 * @author kanven
 *
 */
public class Substring {

	public static int max(String x, String y) {
		return max(x.toCharArray(), y.toCharArray());
	}

	public static int max(char[] x, char[] y) {
		int longest = 0;
		int lx = x.length;
		int ly = y.length;
		for (int i = 0; i < lx; i++) {
			int m = i;
			int n = 0;
			int len = 0;
			while (m < lx && n < ly) {
				if (x[m] == y[n]) {
					len++;
					if (len > longest) {
						longest = len;
					}
				} else {
					len = 0;
				}
				m++;
				n++;
			}
		}
		for (int i = 0; i < ly; i++) {
			int m = i;
			int n = 0;
			int len = 0;
			while (m < ly && n < lx) {
				if (y[m] == x[n]) {
					len++;
					if (len > longest) {
						longest = len;
					} else {
						len = 0;
					}
				}
				m++;
				n++;
			}
		}
		return longest;
	}

	public static String sub(String x, String y) {
		return sub(x.toCharArray(), y.toCharArray());
	}

	public static String sub(char[] x, char[] y) {
		int longest = 0;
		int lx = x.length;
		int ly = y.length;
		int start = -1;
		for (int i = 0; i < lx; i++) {
			int m = i;
			int n = 0;
			int len = 0;
			while (m < lx && n < ly) {
				if (x[m] == y[n]) {
					len++;
					if (len > longest) {
						longest = len;
						start = m - longest + 1;
					}
				} else {
					len = 0;
				}
				m++;
				n++;
			}
		}
		for (int i = 0; i < ly; i++) {
			int m = i;
			int n = 0;
			int len = 0;
			while (m < ly && n < lx) {
				if (y[m] == x[n]) {
					len++;
					if (len > longest) {
						longest = len;
						start = n - longest + 1;
					} else {
						len = 0;
					}
				}
				m++;
				n++;
			}
		}
		if (start > -1) {
			char[] chs = Arrays.copyOfRange(x, start, start + longest);
			return new String(chs);
		}
		return "";
	}

}
