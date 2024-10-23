package com.gateway.handler;

import com.gateway.manager.AuthenticationManager;
import com.gateway.manager.AuthenticationStrategy;
import com.sun.net.httpserver.HttpExchange;

public class AuthenticationHandler {
	
	public void handleAuthentication(HttpExchange httpExchange) {
		AuthenticationManager authManager = AuthenticationStrategy.getAuthenticationManager(httpExchange);
		if (authManager != null) {
			authManager.authenticate(httpExchange);
		} else {
			// TODO: throw exception
		}
	}
}
