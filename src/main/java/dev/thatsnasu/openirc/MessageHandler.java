package dev.thatsnasu.openirc;

import dev.thatsnasu.openirc.exceptions.MessageLengthExceededException;
import dev.thatsnasu.openirc.exceptions.MessagePrefixException;

public class MessageHandler {
	
	public MessageHandler() {
		
	}
	
	
	public Message tokenize(String message) throws MessagePrefixException, MessageLengthExceededException {
		String prefix = "";
		String command = "";
		String parameters = "";
		
		if(message.startsWith(":")) {
			prefix = message.substring(0, message.indexOf(" "));
		}
		
		int whitespaceIndex = message.indexOf(" ");
		command = message.substring(0, whitespaceIndex);
		parameters = message.substring(whitespaceIndex+1);
		
		return new Message(prefix, command, parameters);
	}
}
