package dev.thatsnasu.openirc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.thatsnasu.openirc.exceptions.IRCMessagingException;

public class TestClient {
	private static IRCServer ircServer;
	private static IRCClient ircClient;
	
		
	@BeforeAll
	@DisplayName("Setup client testing environment")
	public static void setup() {
		TestClient.ircServer = new IRCServer(13337);
		TestClient.ircServer.start();
		
		TestClient.ircClient = new IRCClient("localhost", 13337);
		TestClient.ircClient.connect();
	}
	
	@Test
	@DisplayName("Client sendMessage")
	public void sendMessage() {
		assertThrows(IRCMessagingException.class, ()-> TestClient.ircClient.sendMessage(null, null));
		assertThrows(IRCMessagingException.class, ()-> TestClient.ircClient.sendMessage(null, ""));
		assertThrows(IRCMessagingException.class, ()-> TestClient.ircClient.sendMessage("", null));
		assertThrows(IRCMessagingException.class, ()-> TestClient.ircClient.sendMessage("", ""));
		assertThrows(IRCMessagingException.class, ()-> TestClient.ircClient.sendMessage(null, "Hello World!"));

		assertDoesNotThrow(() -> TestClient.ircClient.sendMessage("thatsnasu", "Hello World!"));
		assertDoesNotThrow(() -> TestClient.ircClient.sendMessage("#thatsnasu", "Hello World!"));
	}
	
	@AfterAll
	@DisplayName("shutting down mockserver and cleaning up")
	public static void shutdownTestEnvironment() {
		TestClient.ircClient.close();
		
		TestClient.ircServer.close();
	}
}
