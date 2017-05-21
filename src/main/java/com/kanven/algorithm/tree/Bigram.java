package com.kanven.algorithm.tree;

public class Bigram {

	private final Lexm left;

	private final Lexm right;

	public Bigram(final Lexm left, final Lexm right) {
		this.left = left;
		this.right = right;
	}

	public String build() {
		StringBuilder builder = new StringBuilder();
		builder.append(left.lexm()).append(right.lexm());
		return builder.toString();
	}

	@Override
	public String toString() {
		return "Bigram [left=" + left + ", right=" + right + "]";
	}

}
