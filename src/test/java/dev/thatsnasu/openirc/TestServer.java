package dev.thatsnasu.openirc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

public class TestServer {
	private static IRCServer ircServer;
	private static IRCClient ircClient;
	
	@BeforeAll
	@DisplayName("Setup server testing environment")
	public static void setup() {
		TestServer.ircServer = new IRCServer(13337);
		TestServer.ircServer.start();
		
		TestServer.ircClient = new IRCClient("localhost", 13337);
	}
}
