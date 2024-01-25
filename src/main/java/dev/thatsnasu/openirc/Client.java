package dev.thatsnasu.openirc;

import java.io.IOException;
import java.net.Socket;

import dev.thatsnasu.openirc.exceptions.MalformedAddressException;

public abstract class Client {
	protected String address;
	protected int port;
	
	protected Socket clientSocket;
	
	public abstract void connect() throws IOException;
	public abstract void disconnect() throws IOException;
	
	// SETTER
	public void setAddress(String address) throws MalformedAddressException {
		if(address.equals("")) throw new MalformedAddressException("Invalid host address supplied, empty address given.");
		this.address = address;
	}
	
	public void setPort(int port) throws MalformedAddressException {
		if(port < 0) throw new MalformedAddressException("Invalid port definition supplied, \""+port+"\" is negative and cannot be used as a port.");
		if(port <= 1024) throw new MalformedAddressException("Invalid port definition supplied, \""+port+"\" is in range of a privileged port.");
		if(port > 65536) throw new MalformedAddressException("Invalid port definition supplied, \""+port+"\" is out of valid range, only 16 bit integer values are allowed.");
		
		// all checks passed, accepting port
		this.port = port;
	}
	
	// GETTER
	public String getAddress() {
		return this.address;
	}
	
	public int getPort() {
		return this.port;
	}
}
