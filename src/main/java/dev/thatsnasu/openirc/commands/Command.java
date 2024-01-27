package dev.thatsnasu.openirc.commands;

public abstract class Command {
	public final String responseCode;
	public final String responseMessage;
	
	
	public Command(String responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	
	public abstract boolean handle();
}