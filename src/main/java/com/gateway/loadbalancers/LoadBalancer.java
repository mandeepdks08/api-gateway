package com.gateway.loadbalancers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.sun.net.httpserver.HttpExchange;

public abstract class LoadBalancer {

	protected abstract String getRedirectionIp(HttpExchange exchange);

	public final void redirect(HttpExchange exchange) {
		try {
			String redirectionIp = getRedirectionIp(exchange);
			redirect(exchange, redirectionIp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void redirect(HttpExchange exchange, String redirectionIp) throws IOException {
		String targetUrl = String.format("https://%s:8080%s", redirectionIp, exchange.getRequestURI().getPath());
		HttpURLConnection connection = (HttpURLConnection) new URL(targetUrl).openConnection();
		connection.setRequestMethod(exchange.getRequestMethod());

		exchange.getRequestHeaders().forEach((key, values) -> {
			for (String value : values) {
				connection.setRequestProperty(key, value);
			}
		});

		if (exchange.getRequestMethod().equalsIgnoreCase("POST")
				|| exchange.getRequestMethod().equalsIgnoreCase("PUT")) {
			connection.setDoOutput(true);
			try (OutputStream out = connection.getOutputStream()) {
				byte[] requestBody = readAllBytes(exchange.getRequestBody());
				out.write(requestBody);
			}
		}

		// Get the response from the microservice
		int responseCode = connection.getResponseCode();
		InputStream responseStream = responseCode < HttpURLConnection.HTTP_BAD_REQUEST ? connection.getInputStream()
				: connection.getErrorStream();

		// Set the response headers to the client
		exchange.getResponseHeaders().putAll(connection.getHeaderFields());

		// Send the microservice's response back to the client
		exchange.sendResponseHeaders(responseCode, connection.getContentLengthLong());
		try (OutputStream out = exchange.getResponseBody()) {
			byte[] responseBody = readAllBytes(responseStream);
			out.write(responseBody);
		}

		exchange.close();
	}

	private byte[] readAllBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] data = new byte[1024]; // Buffer size of 1KB
		int bytesRead;

		while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, bytesRead);
		}

		return buffer.toByteArray();
	}

}
