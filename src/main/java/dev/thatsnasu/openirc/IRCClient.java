package dev.thatsnasu.openirc;

import java.net.URL;

import dev.bitbite.networking.Client;

public class IRCClient extends Client {

	
	public IRCClient(URL url) {
		super(url);
	}
	
	public IRCClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void processReceivedData(byte[] data) {
		
	}

}
