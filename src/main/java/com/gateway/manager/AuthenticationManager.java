package com.gateway.manager;

import com.sun.net.httpserver.HttpExchange;

public interface AuthenticationManager {
	public boolean supports(HttpExchange httpExchange);
	public boolean authenticate(HttpExchange httpExchange);
}
