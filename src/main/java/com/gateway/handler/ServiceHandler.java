package com.gateway.handler;

import javax.annotation.Nonnull;

import com.gateway.datamodel.ServiceType;
import com.gateway.manager.LoadBalancer;
import com.gateway.manager.RateLimiter;
import com.sun.net.httpserver.HttpExchange;

public class ServiceHandler {

	private final ServiceType serviceType;
	private RateLimiter rateLimiter;
	private LoadBalancer loadBalancer;

	public ServiceHandler(@Nonnull ServiceType serviceType, @Nonnull RateLimiter rateLimiter,
			@Nonnull LoadBalancer loadBalancer) {
		this.serviceType = serviceType;
		this.rateLimiter = rateLimiter;
		this.loadBalancer = loadBalancer;
	}

	public void redirect(HttpExchange exchange) {
		boolean isRequestAllowed = rateLimiter.requestAllowed(exchange);
		if (isRequestAllowed) {
			try {
				loadBalancer.redirect(exchange);
			} finally {
				rateLimiter.requestCompleted(exchange);
			}
		}
	}

}
