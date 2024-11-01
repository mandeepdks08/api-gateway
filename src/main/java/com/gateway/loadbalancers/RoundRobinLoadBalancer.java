package com.gateway.loadbalancers;

import java.util.List;

import com.sun.net.httpserver.HttpExchange;

public class RoundRobinLoadBalancer extends LoadBalancer {

	private List<String> instanceIps;
	private int lastIndex;

	public RoundRobinLoadBalancer(List<String> instanceIps) {
		this.instanceIps = instanceIps;
		lastIndex = -1;
	}

	@Override
	protected String getRedirectionIp(HttpExchange exchange) {
		int nextIndex = (lastIndex + 1) % instanceIps.size();
		String redirectionIp = instanceIps.get(nextIndex);
		lastIndex = nextIndex;
		return redirectionIp;
	}

}
