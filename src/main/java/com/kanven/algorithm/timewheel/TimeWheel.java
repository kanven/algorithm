package com.kanven.algorithm.timewheel;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 时间轮定时器
 * 
 * @author kanven
 *
 */
public class TimeWheel<T> {

	private static final int STATUS_INIT = 0; // 时间轮初始化

	private static final int STATUS_START = 1; // 时间轮启动

	private static final int STATUS_SHUTDOWN = -1; // 时间轮关闭

	// 时间间隔
	private int tick;

	// 时间间隔单位
	private TimeUnit unit;

	// 时间槽总数
	private int slots;

	private Slot<T>[] wheel;

	private volatile int cursor;

	@SuppressWarnings("unused")
	private volatile int status = STATUS_INIT;

	private ScheduledExecutorService executor;

	private Queue<Slot<T>> queue = new ConcurrentLinkedQueue<Slot<T>>();

	@SuppressWarnings("rawtypes")
	private static final AtomicIntegerFieldUpdater<TimeWheel> STATUS_UPDATER;

	static {// 无锁设计
		STATUS_UPDATER = AtomicIntegerFieldUpdater.newUpdater(TimeWheel.class, "status");
	}

	public TimeWheel() {
		this(1, 1000, TimeUnit.MILLISECONDS);
	}

	public TimeWheel(int tick, int slots, TimeUnit unit) {
		this.tick = tick;
		this.slots = slots;
		this.unit = unit;
	}

	@SuppressWarnings("unchecked")
	public void start() {
		switch (STATUS_UPDATER.get(this)) {
		case STATUS_INIT: {
			if (STATUS_UPDATER.compareAndSet(this, STATUS_INIT, STATUS_START)) {
				wheel = (Slot<T>[]) new Slot[slots];
				executor = Executors.newScheduledThreadPool(1);
				cursor = Calendar.getInstance().get(Calendar.MILLISECOND); // 获取当前毫秒数
				executor.scheduleAtFixedRate(new Runnable() {
					public void run() {
						cursor += tick;
						cursor = cursor >= slots ? 0 : cursor;
						try {
							Slot<T> slot = wheel[cursor];
							if (slot != null) {
								queue.offer(slot);
							}
							wheel[cursor] = new Slot<T>(cursor);
						} catch (Exception e) {
						}
					}
				}, 0, tick, unit);
			}
			break;
		}
		case STATUS_START:
			break;
		case STATUS_SHUTDOWN:
			throw new IllegalStateException("时间轮定时器已经关闭，不能再次启动！");
		}
	}

	public void addTask(T task, int delay) {
		if (delay > 0) {
			start();
			wheel[cursor + delay].add(task);
		}
	}

	public void shutDown() {
		if (!STATUS_UPDATER.compareAndSet(this, STATUS_START, STATUS_SHUTDOWN)) {
			STATUS_UPDATER.set(this, STATUS_SHUTDOWN);
			return;
		}
		// 清理操作
		executor.shutdown();
		wheel = null;
	}

	public class Slot<S> {

		int index;

		private List<S> tasks = new LinkedList<S>();

		public Slot(int index) {
			this.index = index;
		}

		public void add(S task) {
			tasks.add(task);
		}

		public void remove(S task) {
			tasks.remove(task);
		}

	}

}
