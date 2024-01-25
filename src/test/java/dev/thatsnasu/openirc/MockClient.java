package dev.thatsnasu.openirc;

import java.io.IOException;
import java.net.Socket;

import dev.thatsnasu.openirc.Client;

public class MockClient extends Client {
	
	public MockClient() {
		try {
			this.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connect() throws IOException {
		this.clientSocket = new Socket("asdf", port);
	}
	
	@Override
	public void disconnect() throws IOException {
		
	}
	
	
	public static void main(String... args) {
		new MockClient();
	}
}
