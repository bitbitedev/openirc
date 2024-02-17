package dev.bitbite.openirc;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.bitbite.networking.Server;
import dev.bitbite.openirc.exceptions.MalformedMessageException;
import dev.bitbite.openirc.exceptions.MessageLengthExceededException;
import dev.bitbite.openirc.exceptions.MessagePrefixException;
import dev.bitbite.openirc.exceptions.UnknownCommandException;

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
		/*
		 * ^(?::([^ ]+?)[ ]+)? 	- matches the start of the line, and captures the prefix (if present). 
		 * 							The prefix is a string that does not contain any spaces and starts with a colon.
		 * 							The prefix is captured without the colon
		 * 							The prefix, if present must contain at least one character
		 * ([A-Z]+|[0-9]{3}){1} - matches the command, and captures it. 
		 * 							The command can be either a sequence of uppercase letters, or a string of 3 digits
		 * 							There must be exactly one command
		 * (?:[ ]+(.*))?$ 		- matches the parameters, and captures them. 
		 * 							The parameters can contain any character
		 */
		Pattern messagePattern = Pattern.compile("^(?::([^ ]+?)[ ]+)?([A-Z]+|[0-9]{3}){1}(?:[ ]+(.*))?$");
		Matcher matcher = messagePattern.matcher(message);

		if(matcher.find()){
			String prefix = matcher.group(1);
			String command = matcher.group(2);
			String params = matcher.group(3);

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
