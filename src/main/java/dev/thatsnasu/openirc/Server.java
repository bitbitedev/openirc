package dev.thatsnasu.openirc;

import java.io.IOException;
import java.net.ServerSocket;

import dev.thatsnasu.openirc.exceptions.MalformedAddressException;

public abstract class Server {
	protected int port;
	
	protected ServerSocket serverSocket;
	

	public abstract ServerSocket create() throws IOException;
	public abstract void shutdown();

	// SETTER
	public void setPort(int port) throws MalformedAddressException {
		if(port < 0) throw new MalformedAddressException("Invalid port definition supplied, \""+port+"\" is negative and cannot be used as a port.");
		if(port > 65536) throw new MalformedAddressException("Invalid port definition supplied, \""+port+"\" is out of valid range, only 16 bit integer values are allowed.");
		
		// all checks passed, accepting port
		this.port = port;
	}
	
	// GETTER
	public int getPort() {
		return this.port;
	}
}
