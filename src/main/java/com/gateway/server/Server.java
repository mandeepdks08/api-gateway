package com.gateway.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.gateway.handler.RequestHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
	
	public void start() throws IOException {
		final int port = 8080;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/", new RequestHandler());
		server.setExecutor(null);
		server.start();
	}
}
