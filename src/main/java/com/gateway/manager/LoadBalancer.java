package com.gateway.manager;

import javax.xml.ws.spi.http.HttpExchange;

public interface LoadBalancer {
	public boolean supports(HttpExchange exchange);

	public void redirect(HttpExchange exchange);
}
