package com.gateway.manager;

import com.sun.net.httpserver.HttpExchange;

public interface LoadBalancer {
	public boolean supports(HttpExchange exchange);

	public void redirect(HttpExchange exchange);
}
