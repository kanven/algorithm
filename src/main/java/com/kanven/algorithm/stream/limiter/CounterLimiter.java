package com.kanven.algorithm.stream.limiter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.kanven.algorithm.stream.Caller;
import com.kanven.algorithm.stream.Limiter;

/**
 * 计算器限流算法
 * 
 * 不足：存在十分致命额临界问题 0 999 1000 1001 2000 | | | | |
 * ---------------------------------------> 请求 100 100 ==> 200 并发数达到2倍limit
 * 
 * @author kanven
 *
 */
public class CounterLimiter implements Limiter {

	/**
	 * 请求数
	 */
	private volatile AtomicInteger request = new AtomicInteger(0);

	/**
	 * 并发数
	 */
	private volatile int limit = 0;

	/**
	 * 时间窗口(单位毫秒)
	 */
	private volatile int interval = 1000;

	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	public CounterLimiter(int limit, int interval) {
		this.limit = limit;
		this.interval = interval;
		executor.scheduleAtFixedRate(new Runnable() {
			public void run() {
				long time = System.currentTimeMillis();
				int old = request.getAndSet(0);
				System.out.println((time - CounterLimiter.this.interval) + "请求量：" + old);
			}
		}, 0, this.interval, TimeUnit.MILLISECONDS);
	}

	public void invoke(Caller caller) {
		int count = request.incrementAndGet();
		if (count <= limit) {
			caller.call();
		} else {
			caller.unCall();
		}
	}

}
