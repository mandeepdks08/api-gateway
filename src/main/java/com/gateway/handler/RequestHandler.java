package com.gateway.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RequestHandler implements HttpHandler {

	private AuthenticationHandler authHandler;

	private RateLimitHandler rateLimitHandler;
	
	private LoadBalanceHandler loadBalanceHandler;

	public RequestHandler() {
		authHandler = new AuthenticationHandler();
		rateLimitHandler = new RateLimitHandler();
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		boolean isAuthenticated = authHandler.handleAuthentication(exchange);
		if (isAuthenticated) {
			boolean isRequestAllowed = rateLimitHandler.requestAllowed(exchange);
			if (isRequestAllowed) {
				loadBalanceHandler.redirect(exchange);
				rateLimitHandler.requestCompleted(exchange);
			} else {

			}
		} else {

		}
	}

}
