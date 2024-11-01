package com.gateway.ratelimiters;

import com.sun.net.httpserver.HttpExchange;

public class FixedWindowCounterRateLimiter implements RateLimiter {

	private int limit;
	private int counter;
	private int windowSizeInMillis;
	private long windowStartTime;

	public FixedWindowCounterRateLimiter(int limit, int windowSizeInMillis) {
		this.limit = limit;
		this.windowSizeInMillis = windowSizeInMillis;
		this.windowStartTime = System.currentTimeMillis();
		this.counter = 0;
	}

	@Override
	public boolean requestAllowed(HttpExchange exchange) {
		synchronized (this) {
			long currentTime = System.currentTimeMillis();
			boolean isCurrentTimePastThePreviousWindow = windowStartTime + windowSizeInMillis < currentTime;
			if (isCurrentTimePastThePreviousWindow) {
				windowStartTime = currentTime;
				counter = 0;
			}
			if (counter < limit) {
				counter++;
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
