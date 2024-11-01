package com.gateway.ratelimiters;

public enum RateLimitingStrategy {
	LEAKY_BUCKET {
		@Override
		public RateLimiter getRateLimiter() {
			// TODO Auto-generated method stub
			return null;
		}
	},
	TOKEN_BUCKET {
		@Override
		public RateLimiter getRateLimiter() {
			// TODO Auto-generated method stub
			return null;
		}
	};

	public abstract RateLimiter getRateLimiter();

}
