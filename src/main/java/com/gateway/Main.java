package com.gateway;

import java.io.IOException;

import com.gateway.server.Server;

public class Main {
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}

}
