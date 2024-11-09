package com.gateway.authmanagers;

import java.util.List;

import com.gateway.utils.CollectionUtils;
import com.gateway.utils.JwtUtil;
import com.sun.net.httpserver.HttpExchange;

public class JwtAuthenticationManager implements AuthenticationManager {

	@Override
	public boolean supports(HttpExchange httpExchange) {
		String token = extractToken(httpExchange);
		return token != null;
	}

	@Override
	public boolean authenticate(HttpExchange httpExchange) {
		String token = extractToken(httpExchange);
		return !JwtUtil.isTokenExpired(token);
	}

	private String extractToken(HttpExchange httpExchange) {
		List<String> authHeader = httpExchange.getRequestHeaders().get("Authorization");
		return CollectionUtils.isNotEmpty(authHeader) ? authHeader.get(0).substring(7) : null;
	}

}
