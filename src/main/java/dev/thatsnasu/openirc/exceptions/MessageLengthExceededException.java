package dev.thatsnasu.openirc.exceptions;

public class MessageLengthExceededException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public MessageLengthExceededException(String message) {
		super(message);
	}
}
