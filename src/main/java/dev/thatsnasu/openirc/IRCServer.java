package dev.thatsnasu.openirc;

import java.nio.charset.Charset;

import dev.bitbite.networking.Server;

public class IRCServer extends Server {
	private Charset charset;

	public IRCServer(int port) {
		super(port);
	}

	@Override
	protected void processReceivedData(String clientAddress, byte[] data) {
		
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
