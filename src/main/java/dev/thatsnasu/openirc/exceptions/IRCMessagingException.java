package dev.thatsnasu.openirc.exceptions;

public class IRCMessagingException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public IRCMessagingException(String message) {
		super(message);
	}
}
