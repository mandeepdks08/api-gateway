package com.gateway.datamodel;

import com.sun.net.httpserver.HttpExchange;

public enum ServiceType {
	USER("/ums");

	private String prefix;

	private ServiceType(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public static ServiceType getServiceType(HttpExchange exchange) {
		for (ServiceType serviceType : ServiceType.values()) {
			String endpoint = exchange.getRequestURI().getPath();
			if (endpoint.startsWith(serviceType.getPrefix())) {
				return serviceType;
			}
		}
		return null;
	}
}
