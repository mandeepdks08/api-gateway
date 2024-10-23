package com.gateway.manager;

import com.sun.net.httpserver.HttpExchange;

public enum AuthenticationStrategy {
	JWT {
		@Override
		public AuthenticationManager getAuthenticationManager() {
			return null;
		}
	},
	OAUTH {
		@Override
		public AuthenticationManager getAuthenticationManager() {
			return null;
		}
	};

	public abstract AuthenticationManager getAuthenticationManager();

	public static AuthenticationManager getAuthenticationManager(HttpExchange httpExchange) {
		for (AuthenticationStrategy authStrategy : AuthenticationStrategy.values()) {
			AuthenticationManager authManager = authStrategy.getAuthenticationManager();
			if (authManager.supports(httpExchange)) {
				return authManager;
			}
		}
		return null;
	}
}
