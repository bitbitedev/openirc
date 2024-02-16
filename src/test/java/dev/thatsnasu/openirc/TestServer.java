package dev.thatsnasu.openirc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.thatsnasu.openirc.exceptions.MalformedMessageException;
import dev.thatsnasu.openirc.exceptions.MessageLengthExceededException;
import dev.thatsnasu.openirc.exceptions.MessagePrefixException;

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

	@Test
	@DisplayName("Message parsing")
	public void messageParsing() {
		try {
			// Test correct parsing of simple command without prefix and parameters
			assertDoesNotThrow(() -> IRCServer.parseMessage("COMMAND"));
			Message message = IRCServer.parseMessage("COMMAND");

			assertNull(message.prefix);
			assertEquals("COMMAND", message.command);
			assertNull(message.parameters);

			// Test correct parsing of simple 3-digit command without prefix and parameters
			assertDoesNotThrow(() -> IRCServer.parseMessage("123"));
			message = IRCServer.parseMessage("123");

			assertNull(message.prefix);
			assertEquals("123", message.command);
			assertNull(message.parameters);

			// Test that lowercase letters or a non-3-digit code are not allowed as command
			assertThrows(MalformedMessageException.class, () -> IRCServer.parseMessage("command"));
			assertThrows(MalformedMessageException.class, () -> IRCServer.parseMessage("1234"));

			// Test correct parsing of Command with prefix
			assertDoesNotThrow(() -> IRCServer.parseMessage(":prefix COMMAND"));
			message = IRCServer.parseMessage(":prefix COMMAND");

			assertEquals("prefix", message.prefix);
			assertEquals("COMMAND", message.command);
			assertNull(message.parameters);

			// Test correct parsing of Command with one parameter
			assertDoesNotThrow(() -> IRCServer.parseMessage("COMMAND param"));
			message = IRCServer.parseMessage("COMMAND param");

			assertNull(message.prefix);
			assertEquals("COMMAND", message.command);
			assertEquals("param", message.parameters);

			// Test correct parsing of Command with prefix and one parameter
			assertDoesNotThrow(() -> IRCServer.parseMessage(":prefix COMMAND params"));
			message = IRCServer.parseMessage(":prefix COMMAND params");

			assertEquals("prefix", message.prefix);
			assertEquals("COMMAND", message.command);
			assertEquals("params", message.parameters);

			// Test correct parsing of Command with prefix and multiple parameters
			assertDoesNotThrow(() -> IRCServer.parseMessage(":prefix COMMAND multiple params"));
			message = IRCServer.parseMessage(":prefix COMMAND multiple params");

			assertEquals("prefix", message.prefix);
			assertEquals("COMMAND", message.command);
			assertEquals("multiple params", message.parameters);

			// Test that a message without a malformed prefix is not allowed
			assertThrows(MalformedMessageException.class, () -> IRCServer.parseMessage("prefix COMMAND multiple params"));
		} catch (MessagePrefixException | MessageLengthExceededException | MalformedMessageException e) {
			e.printStackTrace();
		}
	}
	
	@AfterAll
	@DisplayName("Teardown and cleanup of server testing environment")
	public static void shutdownTestEnvironment() {
		TestServer.ircClient.close();
		
		TestServer.ircServer.close();
	}
}
