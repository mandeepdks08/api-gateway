package com.gateway.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RequestHandler implements HttpHandler {
	
	private AuthenticationHandler authHandler;

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		
	}

}
