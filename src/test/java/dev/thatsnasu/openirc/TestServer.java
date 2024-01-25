package dev.thatsnasu.openirc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import dev.thatsnasu.openirc.exceptions.*;

import java.io.IOException;
import java.net.ServerSocket;

public class TestServer extends Server {

	// FUNCTIONALITY
	@Override
	public ServerSocket create() throws IOException {
		this.serverSocket = new ServerSocket();
		return this.serverSocket;
	}
	
	@Override
	public void shutdown() {
		
	}
	
	
	// TESTS
	@BeforeAll
	@DisplayName("Setup server testing environment")
	public static void setup() {
		assertDoesNotThrow(() -> new TestServer());
		
		assertNotNull(new TestServer(), "Could not create a server object");
	}
	
	@Test
	@DisplayName("Server port definitons")
	public void checkServerPortDefinitions() {
		// port out of valid range, negatives not allowed
		assertThrows(MalformedAddressException.class, () -> this.setPort(-1));
		// port out of range, integers > 65536 are not allowed
		assertThrows(MalformedAddressException.class, () -> this.setPort(65537));
		
		try {
			this.setPort(1234);
		} catch (MalformedAddressException e) {
			e.printStackTrace();
		}
		assertEquals(1234, this.getPort(), "Port missmatch while retrieving as int from int input");
	}

	@Test
	@DisplayName("Server create")
	public void checkServerCreation() {
		assertDoesNotThrow(() -> this.create());
	}
}
