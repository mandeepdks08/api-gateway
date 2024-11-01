package com.gateway.ratelimiters;

import com.sun.net.httpserver.HttpExchange;

public interface RateLimiter {
	public boolean requestAllowed(HttpExchange exchange);

	public void requestCompleted(HttpExchange exchange);
}
