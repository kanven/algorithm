package com.kanven.algorithm.lru;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 最近最少使用算法实现
 * 
 * <br>
 * 固定条目</br>
 * 
 * @author kanven
 *
 */
public class LRU<T> {

	private final int capacity;

	private Item<T> head = new Item<T>();

	private Item<T> tail = new Item<T>();

	private AtomicInteger count = new AtomicInteger(0);

	private Object lock = new Object();

	public LRU(int capacity) {
		this.capacity = capacity;
		head.next = tail;
		tail.next = head;
	}

	public void add(T v) {
		if (count.get() > capacity) {
			return;
		}
		count.incrementAndGet();
		Item<T> item = new Item<T>(v);
		synchronized (lock) {
			Item<T> next = head.next;
			item.next = next;
			head.next = item;
		}
	}

	public T get() {
		synchronized (lock) {
			Item<T> item = head.next;
			head.next = item.next;
			item.next = tail;
			tail.next.next = item;
			return item.value;
		}
	}

	private static class Item<I> {

		private I value;

		private Item<I> next;

		public Item() {

		}

		public Item(I value) {
			this.value = value;
		}

	}

}
