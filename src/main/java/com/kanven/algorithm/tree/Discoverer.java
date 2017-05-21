package com.kanven.algorithm.tree;

import java.io.IOException;
import java.io.Reader;
import java.lang.Character.UnicodeBlock;
import java.util.Arrays;
import java.util.LinkedList;

public class Discoverer {

	private static final int SIZR = 1024;

	private char[] buffer;

	private Reader reader;

	private int cursor = 0;

	private int tail = 0;

	private LinkedList<Bigram> grams = new LinkedList<Bigram>();

	private LinkedList<Lexm> lexms = new LinkedList<Lexm>();

	public Discoverer(Reader reader) {
		buffer = new char[SIZR];
		this.reader = reader;
		read(0, SIZR);
	}

	private void next() {
		if ((grams.pollFirst()) == null || lexms.pollFirst() == null) {
			char[] chs = analyze(); // 获取文本分段
			
		}
	}

	private char[] analyze() {
		int start = cursor;
		int end = -1;
		while (cursor < tail) {
			char c = buffer[cursor++];
			if (!isChines(c)) {
				if (cursor > start + 1) {
					end = cursor;
					break;
				}
				// 开始位置非中文跳过
				start = cursor;
				continue;
			}
			Lexm lexm = Dictionary.getInstance().matchStop(buffer, cursor);
			if (lexm.getLength() > 0) {
				end = cursor;
				cursor += lexm.getLength();
				if (end > start + 1) {
					break;
				}
				// 开始位置为停词
				start = cursor;
				continue;
			}
		}
		if (end == -1) {
			end = cursor;
		}
		return Arrays.copyOfRange(buffer, start, end);
	}

	private void read(int start, int count) {
		try {
			int len = 0;
			if ((start + count) < SIZR) {
				len = reader.read(buffer, start, count);
			} else {
				len = reader.read(buffer, start, SIZR - start);
			}
			tail = start + len;
		} catch (IOException e) {

		}
	}

	/**
	 * 检测是否是中文
	 * 
	 * @param c
	 * @return
	 */
	private boolean isChines(char c) {
		UnicodeBlock block = UnicodeBlock.of(c);
		if (block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| block == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
			return true;
		}
		return false;
	}

}
