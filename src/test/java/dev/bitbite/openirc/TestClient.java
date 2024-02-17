package dev.bitbite.openirc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.bitbite.openirc.IRCClient;
import dev.bitbite.openirc.IRCServer;
import dev.bitbite.openirc.exceptions.MessagingException;

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
	@DisplayName("Client Charset handling")
	public void charsetHandling() {
		assertThrows(IllegalCharsetNameException.class, () ->TestClient.ircClient.setCharset(""), "Empty strings are not allowed as charsets");
		assertThrows(UnsupportedCharsetException.class, () ->TestClient.ircClient.setCharset("asdf"), "Undefined charsets are not allowed / provided charsetname could not be resolved to a charset.");
		assertDoesNotThrow(() ->TestClient.ircClient.setCharset("UTF-8"), "UTF-8 is a valid charset, should be accepted.");
		assertDoesNotThrow(() ->TestClient.ircClient.setCharset(StandardCharsets.UTF_8), "UTF_8 is a valid charset, should be accepted.");
		
		assertEquals(Charset.defaultCharset(), TestClient.ircClient.getCharset());
		
		TestClient.ircClient.setCharset("UTF-8");
		assertEquals(StandardCharsets.UTF_8, TestClient.ircClient.getCharset());
	}
	
	@Test
	@DisplayName("Client sendMessage")
	public void sendMessage() {
		assertThrows(MessagingException.class, ()-> TestClient.ircClient.sendMessage(null, null));
		assertThrows(MessagingException.class, ()-> TestClient.ircClient.sendMessage(null, ""));
		assertThrows(MessagingException.class, ()-> TestClient.ircClient.sendMessage("", null));
		assertThrows(MessagingException.class, ()-> TestClient.ircClient.sendMessage("", ""));
		assertThrows(MessagingException.class, ()-> TestClient.ircClient.sendMessage(null, "Hello World!"));

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
