package com.gateway;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;

public class Main {
	public static void main(String[] args) throws IOException {
		final int port = 8080;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/", new MyHandler());
		server.setExecutor(null);
		server.start();
		System.out.println("Server listening on port " + port);
	}

	static class MyHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String response = "Hello, this is the response from your server!";
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}

	}
}
