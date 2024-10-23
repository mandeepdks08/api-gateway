package com.gateway.manager;

import javax.xml.ws.spi.http.HttpExchange;

public interface RateLimiter {
	public boolean requestAllowed(HttpExchange exchange);

	public void requestCompleted(HttpExchange exchange);
}
