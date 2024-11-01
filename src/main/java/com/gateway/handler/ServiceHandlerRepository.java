package com.gateway.handler;

import java.util.HashMap;
import java.util.Map;

import com.gateway.datamodel.ServiceType;

public class ServiceHandlerRepository {

	private static Map<ServiceType, ServiceHandler> serviceHandlers;

	static {
		serviceHandlers = new HashMap<>();
		for (ServiceType serviceType : ServiceType.values()) {
			ServiceHandler serviceHandler = new ServiceHandler(serviceType, serviceType.getDefaultRateLimiter());
			serviceHandlers.put(serviceType, serviceHandler);
		}
	}

	public static ServiceHandler getServiceHandler(ServiceType serviceType) {
		return serviceHandlers.get(serviceType);
	}
}
