package dev.thatsnasu.openirc;

import java.net.URL;

import dev.bitbite.networking.Client;
import dev.thatsnasu.openirc.exceptions.IRCMessagingException;

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
	
	public void sendMessage(String target, String message) throws IRCMessagingException {
		if(target == null) throw new IRCMessagingException("Could not process message, target is null.");
		if(target.equals("")) throw new IRCMessagingException("Could not process message, target is empty.");
		if(message == null) throw new IRCMessagingException("Could not process message, null given.");
		if(message.equals("")) throw new IRCMessagingException("Could not process message, empty string given.");
		
		// checks passed
		this.send(message.getBytes());
	}
}
