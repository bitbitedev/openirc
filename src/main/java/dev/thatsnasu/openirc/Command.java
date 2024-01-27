package dev.thatsnasu.openirc;

public abstract class Command {
	public final Identifier identifier;
	protected IRCServer server;
	
	public Command(IRCServer server, String numeric, String named) {
		this.server = server;
		this.identifier = new Identifier(numeric, named);
	}
	
	public abstract void handle(Message message);
	
	@Override
	public String toString() {
		return this.identifier.named+" ("+this.identifier.numeric+")";
	}
}