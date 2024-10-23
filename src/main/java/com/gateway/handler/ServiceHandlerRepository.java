package com.gateway.handler;

import java.util.HashMap;
import java.util.Map;

import com.gateway.datamodel.ServiceType;

public class ServiceHandlerRepository {

	private static Map<ServiceType, ServiceHandler> serviceHandlers;

	static {
		serviceHandlers = new HashMap<>();
	}

	public static ServiceHandler getServiceHandler(ServiceType serviceType) {
		return serviceHandlers.get(serviceType);
	}
}
