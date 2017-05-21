package com.kanven.algorithm.tree;

public final class Lexm {

	private final char[] chs;

	private final int offset;

	private final int length;

	public Lexm(char[] chs, int offset) {
		this.chs = chs;
		this.offset = offset;
		this.length = chs != null ? chs.length : 0;
	}

	public String lexm() {
		return String.valueOf(chs);
	}

	public int getOffset() {
		return offset;
	}

	public int getLength() {
		return length;
	}

	public boolean isDir() {
		return length > 1 ? true : false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Lexm)) {
			return false;
		}
		Lexm lexm = (Lexm) obj;
		if (lexm.offset != this.offset || lexm.length != this.length) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Lexm [chs=" + String.valueOf(chs) + ", offset=" + offset + ", length=" + length + "]";
	}

}
