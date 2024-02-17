package dev.bitbite.openirc.exceptions;

public class DuplicateCommandException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DuplicateCommandException(String message) {
		super(message);
	}
}
