package dev.thatsnasu.openirc;

import dev.bitbite.networking.Server;

public class IRCServer extends Server {

	public IRCServer(int port) {
		super(port);
	}

	@Override
	protected void processReceivedData(String clientAddress, byte[] data) {
		
	}

}
