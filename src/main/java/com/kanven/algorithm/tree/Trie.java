package com.kanven.algorithm.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

	private TrieNode root = new TrieNode(null);

	public void addWord(char[] chs) {
		TrieNode node = root;
		for (int i = 0, len = chs.length; i < len; i++) {
			Character character = new Character(chs[i]);
			if (node.chidlren.containsKey(character)) {
				node = node.chidlren.get(character);
				continue;
			}
			TrieNode child = new TrieNode(character);
			node.chidlren.put(character, child);
			if (i == len - 1) {
				child.setFlag(true);
			}
			node = child;
		}
	}

	/**
	 * 判断是否是词库词
	 * 
	 * @param chs
	 * @return
	 */
	public boolean match(char[] chs) {
		TrieNode node = root;
		for (char c : chs) {
			Character character = new Character(c);
			TrieNode child = node.chidlren.get(character);
			if (child != null) {
				node = child;
				continue;
			}
			return false;
		}
		if (node == null || !node.flag) {
			return false;
		}
		return true;
	}

	/**
	 * 判断左右词是否是词库词
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public boolean match(char[] left, char[] right) {
		if (left == null || right == null || left.length <= 1 || right.length <= 1) {
			return false;
		}
		TrieNode node = root;
		for (char c : left) {
			Character character = new Character(c);
			TrieNode child = node.chidlren.get(character);
			if (child == null) {
				return false;
			}
			node = child;
		}
		for (char c : right) {
			Character character = new Character(c);
			TrieNode child = node.chidlren.get(character);
			if (child == node) {
				return false;
			}
			node = child;
		}
		return node.flag;
	}

	/**
	 * 最大匹配
	 * 
	 * @param chs
	 * @param offset
	 * @return
	 */
	public Lexm matchMax(char[] chs, int offset) {
		int cursor = offset;
		TrieNode node = root;
		int len = chs.length;
		int matched = -1;
		while (cursor < len) {
			char c = chs[cursor++];
			Character character = new Character(c);
			TrieNode child = node.chidlren.get(character);
			if (child == null) {
				break;
			}
			if (child.flag) {
				matched = cursor;
			}
			node = child;
		}
		return matched > -1 ? new Lexm(Arrays.copyOfRange(chs, offset, matched), offset)
				: new Lexm(Arrays.copyOfRange(chs, offset, cursor), offset);
	}

	/**
	 * 最小匹配
	 * 
	 * @param chs
	 * @param offset
	 * @return
	 */
	public Lexm match(char[] chs, int offset) {
		int cursor = offset;
		int len = chs.length;
		TrieNode node = root;
		while (cursor < len) {
			char c = chs[cursor++];
			Character character = new Character(c);
			TrieNode child = node.chidlren.get(character);
			if (child == null || child.flag) {
				break;
			}
			node = child;
		}
		return new Lexm(Arrays.copyOfRange(chs, offset, cursor), offset);
	}

	/**
	 * 全部匹配
	 * 
	 * @param chs
	 * @param offset
	 * @return
	 */
	public List<Lexm> matchAll(char[] chs, int offset) {
		List<Lexm> lexms = new ArrayList<Lexm>();
		int cursor = offset;
		TrieNode node = root;
		while (cursor < chs.length) {
			char c = chs[cursor++];
			Character character = new Character(c);
			TrieNode child = node.chidlren.get(character);
			if (child == null) {
				if (node == root) {
					lexms.add(new Lexm(Arrays.copyOfRange(chs, offset, cursor), offset));
				}
				break;
			}
			if (child.flag) {
				lexms.add(new Lexm(Arrays.copyOfRange(chs, offset, cursor), offset));
			}
			node = child;
		}
		return lexms;
	}

	private static class TrieNode {

		Character key;

		boolean flag = false;

		private Map<Character, TrieNode> chidlren = new HashMap<Character, TrieNode>();

		public TrieNode(Character key) {
			this.key = key;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

		@Override
		public String toString() {
			return "TrieNode [key=" + key + ", flag=" + flag + ", chidlren=" + chidlren + "]";
		}

	}

}
