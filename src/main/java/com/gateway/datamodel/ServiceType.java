package com.gateway.datamodel;

import java.util.ArrayList;
import java.util.List;

import com.gateway.loadbalancers.LoadBalancer;
import com.gateway.loadbalancers.RoundRobinLoadBalancer;
import com.gateway.ratelimiters.RateLimiter;
import com.gateway.ratelimiters.TokenBucketRateLimiter;
import com.sun.net.httpserver.HttpExchange;

public enum ServiceType {
	USER("/ums") {
		@Override
		public List<String> getInstanceIps() {
			return new ArrayList<>();
		}

		@Override
		public RateLimiter getDefaultRateLimiter() {
			int totalInstances = getInstanceIps().size();
			int maxConcurrentRequestsPerInstance = 100;
			int bucketSize = totalInstances * maxConcurrentRequestsPerInstance;
			int tokenGenerationRatePerInstance = 5;
			int tokenGenerationRate = tokenGenerationRatePerInstance * totalInstances;
			int tokenGenerationPeriodInSeconds = 5;
			LoadBalancer loadBalancer = getDefaultLoadBalancer();
			return new TokenBucketRateLimiter(bucketSize, tokenGenerationRate, tokenGenerationPeriodInSeconds,
					loadBalancer);
		}

		@Override
		protected LoadBalancer getDefaultLoadBalancer() {
			return new RoundRobinLoadBalancer(getInstanceIps());
		}

	};

	private String prefix;

	private ServiceType(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public abstract List<String> getInstanceIps();

	public abstract RateLimiter getDefaultRateLimiter();

	protected abstract LoadBalancer getDefaultLoadBalancer();

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
