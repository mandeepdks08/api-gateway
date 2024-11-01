package com.gateway.ratelimiters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.gateway.loadbalancers.LoadBalancer;
import com.sun.net.httpserver.HttpExchange;

public class TokenBucketRateLimiter implements RateLimiter {

	private int bucketSize;
	private int tokenGenerationRate;
	private int availableTokens;
	private LoadBalancer loadBalancer;
	private ScheduledFuture<?> scheduledFuture;

	public TokenBucketRateLimiter(int bucketSize, int tokenGenerationRate, int tokenGenerationTimePeriodInSeconds,
			LoadBalancer loadBalancer) {
		this.bucketSize = bucketSize;
		this.tokenGenerationRate = tokenGenerationRate;
		this.availableTokens = this.bucketSize;
		this.loadBalancer = loadBalancer;
		startGeneratingTokens(tokenGenerationTimePeriodInSeconds);
	}

	private void startGeneratingTokens(int tokenGenerationTimePeriodInSeconds) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		int initialDelay = 0;
		scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
			synchronized (this) {
				availableTokens = Math.min(availableTokens + tokenGenerationRate, bucketSize);
			}
		}, initialDelay, tokenGenerationTimePeriodInSeconds, TimeUnit.SECONDS);
	}

	@Override
	public boolean requestAllowed(HttpExchange exchange) {
		synchronized (this) {
			if (availableTokens == 0) {
				return false;
			} else {
				availableTokens--;
			}
		}
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(() -> loadBalancer.redirect(exchange));
		return true;
	}

	@Override
	public void requestCompleted(HttpExchange exchange) {
		// TODO Auto-generated method stub

	}

}
