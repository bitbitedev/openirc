package dev.thatsnasu.openirc;

import java.nio.charset.Charset;

import dev.bitbite.networking.Server;
import dev.thatsnasu.openirc.exceptions.UnknownCommandException;

public class IRCServer extends Server {
	private Charset charset;
	private CommandHandler commandHandler;

	public IRCServer(int port) {
		super(port);
		this.commandHandler = new CommandHandler("dev.thatsnasu.openirc.commands");
	}
	
	public void initialize() {
		this.commandHandler.loadCommands();
		
		this.start();
	}

	@Override
	protected void processReceivedData(String clientAddress, byte[] data) {
		try {
			this.commandHandler.processMessage(new String(data, this.charset));
		} catch (UnknownCommandException e) {
			e.printStackTrace();
		}
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
