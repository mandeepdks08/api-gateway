package com.gateway.handler;

import java.io.IOException;

import com.gateway.datamodel.ServiceType;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RequestHandler implements HttpHandler {

	private AuthenticationHandler authHandler;

	public RequestHandler() {
		authHandler = new AuthenticationHandler();
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		boolean isAuthenticated = authHandler.handleAuthentication(exchange);
		if (isAuthenticated) {
			ServiceType serviceType = ServiceType.getServiceType(exchange);
			ServiceHandler serviceHandler = ServiceHandlerRepository.getServiceHandler(serviceType);
			serviceHandler.redirect(exchange);
		} else {

		}
	}

}
