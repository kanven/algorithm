package com.kanven.algorithm.lfu;

import java.util.TreeMap;

/**
 * 最近最少访问 <br>
 * <b>核心思想:如果数据过去被访问多次，那么将来被访问的频率也更高 </b></br>
 * 
 * @author kanven
 *
 */
public class LFU<K, V> {

	private final int capacity;

	private TreeMap<Item<K>, V> cache;

	private int count = 0;

	public LFU(int capacity) {
		this.capacity = capacity;
		this.cache = new TreeMap<Item<K>, V>();
	}

	public V get(K key) {
		Item<K> k = new Item<K>(key);
		V value = cache.get(k);
		if (value != null) {

		}
		return value;
	}

	public void put(K key, V value) {
		if (count < capacity) {
			count++;
		} else {

		}
		cache.put(new Item<K>(key), value);
	}

	public V remove(K key) {
		V value = null;
		if (cache.containsKey(key)) {
			value = cache.remove(key);
			count--;
		}
		return value;
	}

	public static class Item<I> implements Comparable<Item<I>> {

		private I value;

		private int freq;

		private long timestamp;

		public Item(I value) {
			this.value = value;
			timestamp = System.currentTimeMillis();
		}

		public void incr() {
			++freq;
			timestamp = System.currentTimeMillis();
		}

		public int compareTo(Item<I> item) {
			if (item == this) {
				return 0;
			}
			if (item == null) {
				return 1;
			}
			if (item.timestamp < timestamp) {
				return 1;
			} else if (item.timestamp > timestamp) {
				return -1;
			}
			if (item.freq <= freq) {
				return 1;
			} else {
				return -1;
			}
		}

		@Override
		public int hashCode() {
			return value.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Item)) {
				return false;
			}
			@SuppressWarnings("unchecked")
			Item<I> o = (Item<I>) obj;
			if (value.equals(o.value)) {
				return true;
			}
			return false;
		}

	}

	public static void main(String[] args) {
		LFU<String, Integer> cache = new LFU<String, Integer>(4);
		cache.put("1", 1);
		cache.put("3", 3);
		cache.put("2", 2);
		cache.put("4", 4);
		cache.get("1");
		cache.get("1");
		cache.get("4");
		cache.get("3");
		cache.put("5", 5);
	}

}
