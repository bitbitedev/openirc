package dev.thatsnasu.openirc;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.bitbite.networking.Server;
import dev.thatsnasu.openirc.exceptions.MalformedMessageException;
import dev.thatsnasu.openirc.exceptions.MessageLengthExceededException;
import dev.thatsnasu.openirc.exceptions.MessagePrefixException;
import dev.thatsnasu.openirc.exceptions.UnknownCommandException;

public class IRCServer extends Server {
	private Charset charset;
	private CommandHandler commandHandler;
	private UserManager userManager;

	public IRCServer(int port) {
		super(port);
		this.commandHandler = new CommandHandler("dev.thatsnasu.openirc.commands");
		this.userManager = new UserManager();
	}
	
	public void initialize() {
		this.commandHandler.loadCommands(this);
		
		this.start();
	}

	@Override
	protected void processReceivedData(String clientAddress, byte[] data) {
		Message message = null;
		try {
			message = parseMessage(new String(data, getCharset()));
		} catch (MessagePrefixException | MessageLengthExceededException | MalformedMessageException e) {
			e.printStackTrace(); // TODO send error code
		}
		if(message != null){
			try {
				this.commandHandler.process(message);
			} catch (UnknownCommandException e) {
				e.printStackTrace(); // TODO send error code
			}
		}
	}

	protected static Message parseMessage(String message) throws MessagePrefixException, MessageLengthExceededException, MalformedMessageException {
		Pattern messagePattern = Pattern.compile("^(:(.*?) )?([^: ][^ ]+){1}( (.*))?$");
		Matcher matcher = messagePattern.matcher(message);

		if(matcher.find()){
			String prefix = matcher.group(2);
			String command = matcher.group(3);
			String params = matcher.group(5);

			return new Message(prefix, command, params);
		}
		throw new MalformedMessageException("Message could not be parsed");
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
	
	public UserManager getUserManager() {
		return this.userManager;
	}
}
