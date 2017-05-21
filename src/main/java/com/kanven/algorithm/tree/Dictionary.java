package com.kanven.algorithm.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Dictionary {

	private static Dictionary instance;

	private Trie _main = new Trie();

	private Trie _stop = new Trie();

	private Dictionary() {

	}

	public static Dictionary getInstance() {
		if (instance == null) {
			instance = DictionaryHolder.DEFAULT_INSTANCE;
		}
		return instance;
	}

	public void loadMain(InputStream in) {
		if (in == null) {
			return;
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line == null) {
					continue;
				}
				_main.addWord(line.toCharArray());
			}
		} catch (IOException e) {
		}
	}

	public void addMain(String word) {
		if (word == null || word.trim() == null) {
			return;
		}
		_main.addWord(word.toCharArray());
	}

	public void loadStop(InputStream in) {
		if (in == null) {
			return;
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line == null) {
					continue;
				}
				_stop.addWord(line.toCharArray());
			}
		} catch (IOException e) {
		}
	}

	public void addStop(String word) {
		if (word == null || word.trim() == null) {
			return;
		}
		_stop.addWord(word.toCharArray());
	}

	public Lexm matchStop(char[] chs, int offset) {
		return _stop.matchMax(chs, offset);
	}

	private static class DictionaryHolder {
		private static final Dictionary DEFAULT_INSTANCE = new Dictionary();
	}

}
