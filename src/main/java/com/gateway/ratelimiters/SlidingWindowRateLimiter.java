package com.gateway.ratelimiters;

import java.util.LinkedList;

import com.sun.net.httpserver.HttpExchange;

public class SlidingWindowRateLimiter implements RateLimiter {

	private int limit;
	private int windowSizeInMillis;
	private LinkedList<Long> requestsTimestamps;

	public SlidingWindowRateLimiter(int limit, int windowSizeInMillis) {
		this.limit = limit;
		this.windowSizeInMillis = windowSizeInMillis;
		this.requestsTimestamps = new LinkedList<>();
	}

	@Override
	public boolean requestAllowed(HttpExchange exchange) {
		synchronized (this) {
			long currentTime = System.currentTimeMillis();
			if (!requestsTimestamps.isEmpty() && requestsTimestamps.getFirst() < (currentTime - windowSizeInMillis)) {
				requestsTimestamps.removeFirst();
			}
			if (requestsTimestamps.size() < limit) {
				requestsTimestamps.add(currentTime);
				return true;
			}
			return false;
		}
	}

	@Override
	public void requestCompleted(HttpExchange exchange) {
		// TODO Auto-generated method stub

	}

}
