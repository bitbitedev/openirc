package dev.thatsnasu.openirc;

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

public class TestServer {
	private static IRCServer ircServer;
	private static IRCClient ircClient;
	
	@BeforeAll
	@DisplayName("Setup server testing environment")
	public static void setup() {
		TestServer.ircServer = new IRCServer(13337);
		TestServer.ircServer.start();
		
		TestServer.ircClient = new IRCClient("localhost", 13337);
		TestServer.ircClient.connect();
	}
	
	@Test
	@DisplayName("Server Charset handling")
	public void charsetHandling() {
		assertThrows(IllegalCharsetNameException.class, () ->TestServer.ircServer.setCharset(""), "Empty strings are not allowed as charsets");
		assertThrows(UnsupportedCharsetException.class, () ->TestServer.ircServer.setCharset("asdf"), "Undefined charsets are not allowed / provided charsetname could not be resolved to a charset.");
		assertDoesNotThrow(() ->TestServer.ircServer.setCharset("UTF-8"), "UTF-8 is a valid charset, should be accepted.");
		assertDoesNotThrow(() ->TestServer.ircServer.setCharset(StandardCharsets.UTF_8), "UTF_8 is a valid charset, should be accepted.");
		
		assertEquals(Charset.defaultCharset(), TestServer.ircServer.getCharset());
		
		TestServer.ircServer.setCharset("UTF-8");
		assertEquals(StandardCharsets.UTF_8, TestServer.ircServer.getCharset());
	}
	
	@AfterAll
	@DisplayName("Teardown and cleanup of server testing environment")
	public static void shutdownTestEnvironment() {
		TestServer.ircClient.close();
		
		TestServer.ircServer.close();
	}
}
