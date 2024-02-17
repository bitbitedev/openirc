package dev.bitbite.openirc.exceptions;

public class MalformedMessageException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public MalformedMessageException(String message) {
		super(message);
	}
}
