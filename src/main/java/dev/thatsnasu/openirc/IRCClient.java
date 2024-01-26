package dev.thatsnasu.openirc;

import java.net.URL;
import java.nio.charset.Charset;

import dev.bitbite.networking.Client;
import dev.thatsnasu.openirc.exceptions.IRCMessagingException;

public class IRCClient extends Client {
	private Charset charset;
	
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
		this.send(message.getBytes((this.charset == null) ? Charset.defaultCharset() : this.charset));
	}
	
	public void setCharset(Charset charset) {
		this.charset = charset;
	}
	
	public void setCharset(String charset) {
		this.setCharset(Charset.forName(charset));
	}
	
	public Charset getCharset() {
		return ((this.charset == null) ? Charset.defaultCharset(): this.charset);
	}
}
