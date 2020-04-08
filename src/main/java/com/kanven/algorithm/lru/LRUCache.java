package com.kanven.algorithm.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 最近最少使用(缓存)算法实现 <br>
 * <b>核心思想:如果数据最近被访问过，那么将来被访问的几率也更高。</b> </br>
 * 
 * @author kanven
 *
 */
public class LRUCache<K, V> {

	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	private final Lock lock = new ReentrantLock();

	private Map<K, V> cache;

	public LRUCache(final int capacity) {
		cache = new LinkedHashMap<K, V>(capacity, DEFAULT_LOAD_FACTOR, true) {

			private static final long serialVersionUID = 6185776996308666343L;

			@Override
			protected boolean removeEldestEntry(Entry<K, V> eldest) {
				return size() > capacity;
			}

		};
	}

	public void put(K key, V value) {
		lock.lock();
		try {
			if (cache.containsKey(key)) {
				return;
			}
			cache.put(key, value);
		} finally {
			lock.unlock();
		}
	}

	public V get(K key) {
		lock.lock();
		try {
			return cache.get(key);
		} finally {
			lock.unlock();
		}
	}

	public int size() {
		lock.lock();
		try {
			return cache.size();
		} finally {
			lock.unlock();
		}
	}

}
