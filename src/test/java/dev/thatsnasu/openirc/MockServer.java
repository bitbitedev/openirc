package dev.thatsnasu.openirc;

import java.io.IOException;
import java.net.ServerSocket;

import dev.thatsnasu.openirc.Server;

public class MockServer extends Server {
	
	public MockServer() {
		try {
			this.create();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ServerSocket create() throws IOException {
		this.serverSocket = new ServerSocket();
		return this.serverSocket;
	}
	
	@Override
	public void shutdown() {
		
	}
	
	// launch point
	public static void main(String... args) {
		new MockServer();
	}
}
