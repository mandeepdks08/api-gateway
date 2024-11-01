package com.gateway.handler;

import javax.annotation.Nonnull;

import com.gateway.datamodel.ServiceType;
import com.gateway.ratelimiters.RateLimiter;
import com.sun.net.httpserver.HttpExchange;

public class ServiceHandler {

	private final ServiceType serviceType;
	private RateLimiter rateLimiter;

	public ServiceHandler(@Nonnull ServiceType serviceType, @Nonnull RateLimiter rateLimiter) {
		this.serviceType = serviceType;
		this.rateLimiter = rateLimiter;
	}

	public void redirect(HttpExchange exchange) {
		boolean isRequestAllowed = rateLimiter.requestAllowed(exchange);
		if (!isRequestAllowed) {

		}
	}

}
