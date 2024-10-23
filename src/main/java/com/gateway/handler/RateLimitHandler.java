package com.gateway.handler;

import com.sun.net.httpserver.HttpExchange;

public class RateLimitHandler {

	public boolean requestAllowed(HttpExchange exchange) {
		return true;
	}

	public void requestCompleted(HttpExchange exchange) {

	}

}
