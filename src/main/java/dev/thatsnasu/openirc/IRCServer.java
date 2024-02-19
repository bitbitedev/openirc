package dev.thatsnasu.openirc;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import dev.bitbite.logging.Log;
import dev.bitbite.logging.exceptions.MessageProcessorException;
import dev.bitbite.networking.Server;
import dev.thatsnasu.openirc.exceptions.MessageLengthExceededException;
import dev.thatsnasu.openirc.exceptions.MessagePrefixException;
import dev.thatsnasu.openirc.exceptions.UnknownCommandException;

public class IRCServer extends Server {
	private Charset charset;
	private CommandHandler commandHandler;
	private MessageHandler messageHandler;
	private UserManager userManager;
	
	public static Log log;

	public IRCServer(int port) {
		super(port);
		super.setVERBOSE(true);
		this.commandHandler = new CommandHandler("dev.thatsnasu.openirc.commands");
		this.messageHandler = new MessageHandler();
		this.userManager = new UserManager();
		
		IRCServer.log = new Log();
		IRCServer.log.addConsumer((message) -> System.out.println(message));
		try {
			IRCServer.log.addMessageProcessor(0, (message) -> {
					SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss - dd.MM.yyyy");
					message = "["+sdf.format(new Date())+"] "+message;
					return message;
				});
		} catch (MessageProcessorException e) {
			e.printStackTrace();
		}
	}
	
	public void initialize() {
		IRCServer.log.debug("Beginning to load commands from package "+this.commandHandler.getPackageName());
		this.commandHandler.loadCommands(this);
		IRCServer.log.debug("Loading commands complete.");
		
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
		return ((this.charset == null) ? Charset.defaultCharset() : this.charset);
	}
	
	public UserManager getUserManager() {
		return this.userManager;
	}
}
