package com.gateway.ratelimiters;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.gateway.loadbalancers.LoadBalancer;
import com.sun.net.httpserver.HttpExchange;

public class LeakyBucketRateLimiter implements RateLimiter {

	private int bucketCapacity;
	private int leakRate;
	private Queue<HttpExchange> pendingRequests;
	private LoadBalancer loadBalancer;
	private ScheduledFuture<?> scheduledFuture;

	public LeakyBucketRateLimiter(int bucketCapacity, int leakRate, int leakPeriodInSeconds,
			LoadBalancer loadBalancer) {
		this.bucketCapacity = bucketCapacity;
		this.leakRate = leakRate;
		this.pendingRequests = new LinkedList<>();
		this.loadBalancer = loadBalancer;
		startLeaking(leakPeriodInSeconds);
	}

	private void startLeaking(int leakPeriodInSeconds) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		int initialDelay = 0;
		scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
			HttpExchange[] poppedRequests = null;
			synchronized (this) {
				poppedRequests = new HttpExchange[Math.min(leakRate, pendingRequests.size())];
				int i = 0;
				while (!this.pendingRequests.isEmpty() && i < poppedRequests.length) {
					poppedRequests[i] = this.pendingRequests.poll();
					i++;
				}
			}
			ExecutorService executor = Executors.newFixedThreadPool(poppedRequests.length);
			for (HttpExchange request : poppedRequests) {
				executor.submit(() -> loadBalancer.redirect(request));
			}
		}, initialDelay, leakPeriodInSeconds, TimeUnit.SECONDS);
	}

	@Override
	public boolean requestAllowed(HttpExchange exchange) {
		synchronized (this) {
			if (pendingRequests.size() < bucketCapacity) {
				pendingRequests.add(exchange);
				return true;
			}
			return false;
		}
	}

	@Override
	public void requestCompleted(HttpExchange exchange) {

	}

}
