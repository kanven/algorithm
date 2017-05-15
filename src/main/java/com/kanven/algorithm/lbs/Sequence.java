package com.kanven.algorithm.lbs;

/**
 * 最长公共子序列
 * 
 * @author kanven
 *
 */
public class Sequence {

	public static String seq(String x, String y) {
		char[] f = x.toCharArray();
		char[] s = y.toCharArray();
		int lf = f.length;
		int ls = s.length;
		int[][] opt = new int[lf + 1][ls + 1];
		for (int i = 1; i < lf; i++) {
			for (int j = 1; j < ls; j++) {
				opt[i][j] = f[i - 1] == s[j - 1] ? opt[i - 1][j - 1] + 1 : Math.max(opt[i - 1][j], opt[i][j - 1]);
			}
		}
		int l = f.length;
		int r = s.length;
		StringBuilder builder = new StringBuilder();
		while (l > 0 && r > 0) {
			if (f[l - 1] == s[r - 1]) {
				builder.append(f[l - 1]);
				l--;
				r--;
			} else if (opt[l - 1][r] == opt[l][r - 1]) {
				// TODO 多子字符串情况需要额外处理
				l--;
			} else if (opt[l - 1][r] > opt[l][r - 1]) {
				l--;
			} else {
				r--;
			}
		}
		builder.reverse();
		return builder.toString();
	}

	public static int max(char[] x, char[] y) {
		int lx = x.length;
		int ly = y.length;
		int[][] opt = new int[lx + 1][ly + 1];
		for (int i = 1; i <= lx; i++) {
			for (int j = 1; j <= ly; j++) {
				opt[i][j] = x[i - 1] == y[j - 1] ? opt[i - 1][j - 1] + 1 : Math.max(opt[i - 1][j], opt[i][j - 1]);
			}
		}
		return opt[lx][ly];
	}

	public static int max(String x, String y) {
		return max(x.toCharArray(), y.toCharArray());
	}

	public static void main(String[] args) {
		System.out.println(Sequence.seq("BDCABA", "ABCBDAB"));
	}

}
