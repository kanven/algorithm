package com.kanven.algorithm.stream.limiter;

import java.util.concurrent.Semaphore;

import com.kanven.algorithm.stream.Caller;
import com.kanven.algorithm.stream.Limiter;

/**
 * 并发数限流
 * 
 * @author kanven
 *
 */
public class ConcurrentLimiter implements Limiter {

	private volatile int concurrent = 200;

	private Semaphore semaphore;

	public ConcurrentLimiter(int concurrent) {
		if (concurrent > 0) {
			this.concurrent = concurrent;
		}
		semaphore = new Semaphore(this.concurrent);
	}

	public void invoke(Caller caller) {
		if (semaphore.tryAcquire()) {
			try {
				caller.call();
			} finally {
				semaphore.release();
			}
		} else {
			caller.unCall();
		}
	}

}
