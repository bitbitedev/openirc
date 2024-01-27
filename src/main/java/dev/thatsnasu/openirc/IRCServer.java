package dev.thatsnasu.openirc;

import java.nio.charset.Charset;

import dev.bitbite.networking.Server;
import dev.thatsnasu.openirc.exceptions.MessageLengthExceededException;
import dev.thatsnasu.openirc.exceptions.MessagePrefixException;

public class IRCServer extends Server {
	private Charset charset;
	private CommandHandler commandHandler;
	private MessageHandler messageHandler;

	public IRCServer(int port) {
		super(port);
		this.commandHandler = new CommandHandler("dev.thatsnasu.openirc.commands");
		this.messageHandler = new MessageHandler();
	}
	
	public void initialize() {
		this.commandHandler.loadCommands();
		
		this.start();
	}

	@Override
	protected void processReceivedData(String clientAddress, byte[] data) {
		try {
			Message message = this.messageHandler.tokenize(new String(data, this.charset));
			
			this.commandHandler.processMessage(message);
		} catch (MessagePrefixException e) {
			e.printStackTrace();
		} catch (MessageLengthExceededException e) {
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
