package dev.thatsnasu.openirc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class TestClient {
	private static IRCServer ircServer;
	private static IRCClient ircClient;
	
		
	@BeforeAll
	@DisplayName("Setup client testing environment")
	public static void setup() {
		TestClient.ircServer = new IRCServer(13337);
		TestClient.ircServer.start();
		
		TestClient.ircClient = new IRCClient("localhost", 13337);
	}
	
	@AfterAll
	@DisplayName("shutting down mockserver and cleaning up")
	public static void shutdownTestEnvironment() {
		TestClient.ircClient.close();
		
		TestClient.ircServer.close();
	}
}
