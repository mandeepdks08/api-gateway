package com.gateway.handler;

import com.gateway.authmanagers.AuthenticationManager;
import com.gateway.authmanagers.AuthenticationStrategy;
import com.sun.net.httpserver.HttpExchange;

public class AuthenticationHandler {

	public boolean handleAuthentication(HttpExchange httpExchange) {
		AuthenticationManager authManager = AuthenticationStrategy.getAuthenticationManager(httpExchange);
		if (authManager != null) {
			return authManager.authenticate(httpExchange);
		}
		return false;
	}
}
