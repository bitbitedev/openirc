package dev.bitbite.openirc;

@FunctionalInterface
public interface Command {

	public abstract String handle(IRCServer server, Message message);
	
}