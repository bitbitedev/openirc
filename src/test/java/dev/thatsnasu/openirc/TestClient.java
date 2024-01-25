package dev.thatsnasu.openirc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.io.IOException;

import dev.thatsnasu.openirc.Client;
import dev.thatsnasu.openirc.exceptions.*;

public class TestClient extends Client {
	private static MockServer mockServer;
	
	// FUNCTIONALITY
	@Override
	public void connect() throws IOException {
		
	}
	
	@Override
	public void disconnect() throws IOException {
		
	}
	
	
	// TESTS	
	@BeforeAll
	@DisplayName("Setup client testing environment")
	public static void setup() {
		assertDoesNotThrow(() -> new TestClient());

		assertNotNull(new TestClient(), "Could not create a client object");
		
		// Setup mock server for running the tests
		TestClient.mockServer = new MockServer();
		try {
			TestClient.mockServer.create();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Client address definitions")
	public void checkClientAddressDefinitions() {
		// empty strings are not allowed
		assertThrows(MalformedAddressException.class, () -> this.setAddress(""));
		
		try {
			this.setAddress("localhost");
		} catch (MalformedAddressException e) {
			e.printStackTrace();
		}
		assertEquals("localhost", this.getAddress(), "Address missmatch while retriving as string");
	}
	
	@Test
	@DisplayName("Client port definiton")
	public void checkClientPortDefinitions() {
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
	@DisplayName("Client connect")
	public void checkClientConnectionCreation() {
		assertDoesNotThrow(() -> this.connect());
	}
	
	@AfterAll
	@DisplayName("shutting down mockserver and cleaning up")
	public static void shutdownTestEnvironment() {
		TestClient.mockServer.shutdown();
	}
}
