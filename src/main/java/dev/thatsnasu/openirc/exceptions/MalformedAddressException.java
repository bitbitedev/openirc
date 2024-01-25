package dev.thatsnasu.openirc.exceptions;

public class MalformedAddressException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public MalformedAddressException(String message) {
		super(message);
	}
}
